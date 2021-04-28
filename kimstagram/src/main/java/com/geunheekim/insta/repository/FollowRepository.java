package com.geunheekim.insta.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.geunheekim.insta.model.Follow;


public interface FollowRepository extends JpaRepository<Follow, Integer>{

	// unFollow
	@Transactional
	void deleteByFromUserIdAndToUserId(int fromUserId, int toUserId);
	
	// 팔로우 유무
	int countByFromUserIdAndToUserId(int fromUserId, int toUserId);
	
	// 팔로잉 리스트 (흰색 버튼)
	List<Follow> findByFromUserId(int fromUserId);
	
	// 팔로워 리스트 (맞팔 체크 후 버튼 색상 결정)
	List<Follow> findByToUserId(int toUserId);
}
