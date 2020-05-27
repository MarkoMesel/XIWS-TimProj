package com.student.internal.contract;

import java.util.UUID;

import javax.validation.constraints.NotEmpty;

public class InternalVerifyRequest{
	@NotEmpty
	private UUID verificationToken;

	public UUID getVerificationToken() {
		return verificationToken;
	}

	public void setVerificationToken(UUID verificationToken) {
		this.verificationToken = verificationToken;
	}
}
