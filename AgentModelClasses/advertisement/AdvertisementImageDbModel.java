package com.siteproj0.demo.advertisement;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
//import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="AdImage")
public class AdvertisementImageDbModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	//@JoinColumn(name="AD_ID")
	private AdvertisementDbModel advertisement;
	
	@Lob
	private byte[] image;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public AdvertisementDbModel getAdvertisement() {
		return advertisement;
	}

	public void setAdvertisement(AdvertisementDbModel advertisement) {
		this.advertisement = advertisement;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}
}
