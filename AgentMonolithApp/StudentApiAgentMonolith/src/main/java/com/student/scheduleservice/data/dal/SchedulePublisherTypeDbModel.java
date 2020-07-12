package com.student.scheduleservice.data.dal;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name="SchedulePublisherType")
public class SchedulePublisherTypeDbModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotEmpty
	private String name;
	
	@OneToMany(mappedBy = "publisherType")
	private List<BundleDbModel> bundles;
	
	@OneToMany(mappedBy = "publisherType")
	private List<MessageDbModel> messages;
	
	@OneToMany(mappedBy = "publisherType")
	private List<CommentDbModel> comments;
	
	@OneToMany(mappedBy = "publisherType")
	private List<PriceListDbModel> priceLists;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
