package com.geunheekim.insta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.geunheekim.insta.model.Image;

public interface ImageRepository extends JpaRepository<Image, Integer>{
	@Query(value = "select * from image where userId in (select toUserId from follow where fromUserId = ?1)", nativeQuery = true)
	List<Image> findImage(int userId);
}
