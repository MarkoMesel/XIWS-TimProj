package com.student.jwt;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import com.student.data.dal.UserDbModel;
import com.student.data.dal.UserPermissionDbModel;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component("JwtUtil")
public class JwtUtil {

	private static String issuer = "user@student.com";
	private static String AUTHENTICATION_STRING = "AUTHENTICATION";

	private PrivateKey privateKey;

	public JwtUtil() {
		try {
			File privateFile = ResourceUtils.getFile("classpath:keys/private_key.der");
			FileInputStream fileInputStream = new FileInputStream(privateFile);
			DataInputStream dataInputStream = new DataInputStream(fileInputStream);
			byte[] keyBytes = new byte[(int) privateFile.length()];
			dataInputStream.readFully(keyBytes);
			dataInputStream.close();
			fileInputStream.close();
			KeyFactory kf = KeyFactory.getInstance("RSA");
			EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
			privateKey = kf.generatePrivate(keySpec);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getPasswordResetToken(int userId) {
		return Jwts.builder().setIssuer(issuer).claim("userId", userId).claim("purpose", "PASSWORD_RESET")
				// RS256 with privateKey
				.signWith(SignatureAlgorithm.RS256, privateKey).compact();
	}

	public String getEmailVerificationToken(int userId) {
		return Jwts.builder().setIssuer(issuer).claim("userId", userId).claim("purpose", "EMAIL_VERIFICATION")
				// RS256 with privateKey
				.signWith(SignatureAlgorithm.RS256, privateKey).compact();
	}

	public String getAuthenticationToken(UserDbModel userDbModel) {
		List<Permission> permissions = new ArrayList<Permission>();
		for (UserPermissionDbModel userPermission : userDbModel.getUserPermissions()) {
			Permission permission = new Permission();
			permission.setPermissionId(userPermission.getPermission().getId());
			permission.setPermissionName(userPermission.getPermission().getName());
			permission.setResourceTypeId(userPermission.getResourseType().getId());
			permission.setResourceTypeName(userPermission.getResourseType().getName());
			permission.setResourceId(userPermission.getResourseId());
			permissions.add(permission);
		}
		return Jwts.builder().setIssuer(issuer)
				.claim("roleName", userDbModel.getRole().getName())
				.claim("roleId", userDbModel.getRole().getId())
				.claim("permissions", permissions)
				.claim("userId", userDbModel.getId())
				.claim("purpose", AUTHENTICATION_STRING)
				// RS256 with privateKey
				.signWith(SignatureAlgorithm.RS256, privateKey).compact();
	}

	public JwtTokenParseResult parseEmailVarificationToken(String token) {
		JwtTokenParseResult result = parseJwtToken(token);
		if (!result.getPurpose().equals("EMAIL_VERIFICATION")) {
			result = new JwtTokenParseResult();
			result.setValid(false);
			return result;
		}
		return result;
	}

	public JwtTokenParseResult parsePasswordResetToken(String token) {
		JwtTokenParseResult result = parseJwtToken(token);
		if (!result.getPurpose().equals("PASSWORD_RESET")) {
			result = new JwtTokenParseResult();
			result.setValid(false);
			return result;
		}
		return result;
	}

	private JwtTokenParseResult parseJwtToken(String token) {
		JwtTokenParseResult result = new JwtTokenParseResult();
		try {

			Jws<Claims> parseClaimsJws = Jwts.parser().setSigningKey(privateKey).parseClaimsJws(token);
			Claims claims = parseClaimsJws.getBody();
			result.setUserId((Integer) claims.get("userId"));
			result.setIssuer((String) claims.get("iss"));
			result.setPurpose((String) claims.get("purpose"));

			if (result.getIssuer() == null || !result.getIssuer().equals(issuer) || result.getUserId() == null
					|| result.getPurpose() == null || (!result.getPurpose().equals("PASSWORD_RESET")
							&& !result.getPurpose().equals("EMAIL_VERIFICATION"))) {
				result = new JwtTokenParseResult();
				result.setValid(false);
				return result;
			}
			result.setValid(true);
			return result;
		} catch (Exception e) {
			result = new JwtTokenParseResult();
			result.setValid(false);
			return result;
		}
	}

	public AuthenticationTokenParseResult parseAuthenticationToken(String token) {
		AuthenticationTokenParseResult result = new AuthenticationTokenParseResult();
		try {
			Jws<Claims> parseClaimsJws = Jwts.parser().setSigningKey(privateKey).parseClaimsJws(token);
			Claims claims = parseClaimsJws.getBody();
			result.setUserId((Integer) claims.get("userId"));
			result.setIssuer((String) claims.get("iss"));
			result.setPurpose((String) claims.get("purpose"));
			Object permissionsObject = claims.get("permissions");

			List<Permission> permissions = new ArrayList<Permission>();
			if (permissionsObject instanceof List) {
				for (int i = 0; i < ((List<?>) permissionsObject).size(); i++) {
					Object item = ((List<?>) permissionsObject).get(i);
					if (item instanceof Permission) {
						permissions.add((Permission) item);
					}
				}
			}

			result.setPermissions((List<Permission>) permissions);
			result.setRoleName((String) claims.get("roleName"));
			result.setRoleId((Integer) claims.get("roleId"));

			if (result.getIssuer() == null || !result.getIssuer().equals(issuer) || result.getUserId() == null
					|| result.getPurpose() == null || !result.getPurpose().equals(AUTHENTICATION_STRING)
					|| (!(result.getRoleName().equals("BASIC") && result.getRoleId() == 1)
							&& !(result.getRoleName().equals("AGENT") && result.getRoleId() == 2)
							&& !(result.getRoleName().equals("ADMIN") && result.getRoleId() == 3))) {
				result = new AuthenticationTokenParseResult();
				result.setValid(false);
				return result;
			}
			result.setValid(true);
			return result;
		} catch (Exception e) {
			result = new AuthenticationTokenParseResult();
			result.setValid(false);
			return result;
		}
	}
}
