package com.geunheekim.insta.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity
public class User {
	
	//시퀀스
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	// 사용자 아이디
	private String username;
	// 사용자 비밀번호
	private String password;
	// 사용자 이름
	private String name;
	// 사용자 홈페이지
	private String website;
	// 사용자 설명
	private String bio;
	// 사용자 이메일
	private String email;
	// 사용자 전화번호
	private String phone;
	// 사용자 성별
	private String gender;
	
	// 사용자 프로필 사진 경로 + 이름
	private String profileImage;
	
	
	// (1) findById() 때만 동작
	// (2) findByUserInfo() 제외
	@OneToMany(mappedBy = "user")
	@JsonIgnoreProperties({"user", "tags", "likes"})
	private List<Image> images = new ArrayList<>();
	
	
	
	@CreationTimestamp		//자동으로 현재 시간 세팅
	private Timestamp createDate;
	@CreationTimestamp		//자동으로 현재 시간 세팅
	private Timestamp updateDate;
	
}
