package com.geunheekim.insta.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity
public class Like {
	//시퀀스
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name="userId")
	@JsonIgnoreProperties({"images", "password", "name", "website", 
		"bio", "email", "phone", "gender", "createDate", "updateDate"})
	private User user;
	
	// 기본: image_id
	@ManyToOne
	@JoinColumn(name="imageId")
	@JsonIgnoreProperties({"tags", "user", "likes"})
	private Image image;
	
	@CreationTimestamp
	private Timestamp createDate;
	@CreationTimestamp
	private Timestamp updateDate;
}
