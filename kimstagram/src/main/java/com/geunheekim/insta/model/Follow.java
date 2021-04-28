package com.geunheekim.insta.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity
public class Follow {

	// 시퀀스
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	// 중간 테이블
	// fromUser가 toUser를 following 함
	// toUser를 fromUser가 follwer 함
	@ManyToOne
	@JoinColumn(name = "fromUserId")
	@JsonIgnoreProperties({"images"})
	private User fromUser;

	@ManyToOne
	@JoinColumn(name = "toUserId")
	@JsonIgnoreProperties({"images"})
	private User toUser;
	
	@Transient
	private boolean followState;
	
	@CreationTimestamp
	private Timestamp createDate;
	@CreationTimestamp
	private Timestamp updateDate;
}
