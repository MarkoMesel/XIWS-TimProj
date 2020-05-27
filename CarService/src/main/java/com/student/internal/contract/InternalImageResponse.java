package com.student.internal.contract;

public class InternalImageResponse extends InternalResponse {
	private byte[] image;

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}
}
