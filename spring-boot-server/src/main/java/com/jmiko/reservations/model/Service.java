package com.jmiko.reservations.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "services")
@Data
public class Service {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "category_id", nullable = false)
	private ServiceCategory category;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "price")
	private BigDecimal price;

	@Column(name = "active")
	private boolean active;

	@Column(name = "date_created")
	@CreationTimestamp
	private Date dateCreated;

	@Column(name = "last_updated")
	@UpdateTimestamp
	private Date lastUpdated;

	@Column(name = "image_url")
	private String imageUrl;



	public Service(Long id, String name, String description, BigDecimal price, boolean active, Date dateCreated, Date lastUpdated, String imageUrl) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.active = active;
		this.dateCreated = dateCreated;
		this.lastUpdated = lastUpdated;
		this.imageUrl = imageUrl;
	}

	public Service() {

	}
}
