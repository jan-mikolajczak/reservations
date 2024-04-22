package com.jmiko.reservations.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name = "tutorials")
@Data
public class Tutorial {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "title")
	private String title;

	@Column(name = "description")
	private String description;

	@Column(name = "published")
	private boolean published;

	@Column(name = "last_update")
	@UpdateTimestamp
	private Date dateCreated;

	public Tutorial() {

	}

	public Tutorial(String title, String description, boolean published, Date dateCreated) {
		this.title = title;
		this.description = description;
		this.published = published;
		this.dateCreated = dateCreated;
	}

}
