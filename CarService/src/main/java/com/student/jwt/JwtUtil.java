package com.student.jwt;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

@Component("JwtUtil")
public class JwtUtil {

	private static String issuer = "user@student.com";
	private static String AUTHENTICATION_STRING = "AUTHENTICATION";

	private PublicKey publicKey;

	public JwtUtil() {
		try {
			File privateFile = ResourceUtils.getFile("classpath:keys/public_key.der");
			FileInputStream fileInputStream = new FileInputStream(privateFile);
			DataInputStream dataInputStream = new DataInputStream(fileInputStream);
			byte[] keyBytes = new byte[(int) privateFile.length()];
			dataInputStream.readFully(keyBytes);
			dataInputStream.close();
			fileInputStream.close();
			KeyFactory kf = KeyFactory.getInstance("RSA");
			EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
			publicKey = kf.generatePublic(keySpec);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public AuthenticationTokenParseResult parseAuthenticationToken(String token) {
		AuthenticationTokenParseResult result = new AuthenticationTokenParseResult();
		try {
			Jws<Claims> parseClaimsJws = Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token);
			Claims claims = parseClaimsJws.getBody();
			result.setUserId((Integer) claims.get("userId"));
			result.setIssuer((String) claims.get("iss"));
			result.setPurpose((String) claims.get("purpose"));
			Object permissionsObject = claims.get("permissions");
			ObjectMapper mapper = new ObjectMapper();
			List<Permission> permissions= mapper.convertValue(permissionsObject, new TypeReference<List<Permission>>() { });
			result.setPermissions(permissions);
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
	
	public boolean isAdmin(AuthenticationTokenParseResult tokenParseResult) {
		return tokenParseResult.getRoleName().equals("ADMIN");
	}
	
	public boolean isResourseAgent(String roleName, Permission permission, Integer resourseId, int resourseTypeId) {
		
		boolean isAgent = roleName.equals("AGENT");
		boolean isAgentPermission = permission.getResourceTypeName()!=null && permission.getResourceTypeName().equals("AGENT");
		boolean isResoursePermission = true;
		if(resourseId != null) {
			isResoursePermission = permission.getResourceId() == resourseId;			
		}
		boolean isAgentResourse = resourseTypeId == 2;
		
		return isAgent && isAgentPermission && isResoursePermission && isAgentResourse;
	}
	
	public boolean isResourseUser(String roleName, Permission permission, Integer resourseId, int resourseTypeId) {
		
		boolean isUser = roleName.equals("BASIC");
		boolean isUserPermission = permission.getResourceTypeName()!=null && permission.getResourceTypeName().equals("USER");
		boolean isResoursePermission = true;
		if(resourseId != null) {
			isResoursePermission = permission.getResourceId() == resourseId;			
		}
		boolean isUserResourse = resourseTypeId == 1;
		
		return isUser && isUserPermission && isResoursePermission && isUserResourse;
	}
	
	public boolean isAutharized(AuthenticationTokenParseResult token, int permissionId, Integer resourseId, int resourseTypeId) {
		Permission requiredPermission = token.getPermissions().stream()
				.filter(permission -> permission.getPermissionId() == permissionId).findFirst().orElse(null);
		
		if (!token.isValid() || requiredPermission == null)
		{
			return false;
		}
		
		boolean isAdmin = isAdmin(token);
		boolean isAgent = isResourseAgent(token.getRoleName(), requiredPermission, resourseId, resourseTypeId);
		boolean isUser =  isResourseUser(token.getRoleName(), requiredPermission, resourseId, resourseTypeId);
		
		if ( !isAdmin && !isAgent && !isUser ) 
		{
			return false;
		}
		
		return true;
	}
}
