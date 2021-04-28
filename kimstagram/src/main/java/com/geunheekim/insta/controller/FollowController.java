package com.geunheekim.insta.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.geunheekim.insta.model.Follow;
import com.geunheekim.insta.model.User;
import com.geunheekim.insta.repository.FollowRepository;
import com.geunheekim.insta.repository.UserRepository;
import com.geunheekim.insta.service.MyUserDetail;

@Controller
public class FollowController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private FollowRepository followRepository;
	
	@PostMapping("/follow/{id}")
	public @ResponseBody String follow(
			@AuthenticationPrincipal MyUserDetail userDetail, @PathVariable int id) 
	{
		User fromUser = userDetail.getUser();
		Optional<User> oToUser = userRepository.findById(id);
		User toUser = oToUser.get();
		
		Follow follow = new Follow();
		follow.setFromUser(fromUser);
		follow.setToUser(toUser);
		
		followRepository.save(follow);
		
		return "ok";
	}
	
	@DeleteMapping("/follow/{id}")
	public @ResponseBody String unFollow(
			@AuthenticationPrincipal MyUserDetail userDetail, @PathVariable int id) 
	{
		User fromUser = userDetail.getUser();
		Optional<User> oToUser = userRepository.findById(id);
		User toUser = oToUser.get();
		
		followRepository.deleteByFromUserIdAndToUserId(fromUser.getId(), toUser.getId());
		
		List<Follow> follows = followRepository.findAll();
		return "ok";
	}
	
	@GetMapping("/follow/follower/{id}")
	public String followFollower(
			@PathVariable int id, 
			@AuthenticationPrincipal MyUserDetail userDetail, 
			Model model) 
	{
		// 팔로워 리스트
		List<Follow> followers = followRepository.findByToUserId(id);
		
		// 당사자의 팔로워 리스트
		List<Follow> principalFollows = followRepository.findByFromUserId(userDetail.getUser().getId());
			
		for(Follow f1 : followers) {
			for(Follow f2 : principalFollows) {
				if(f1.getFromUser().getId() == (f2.getToUser().getId())) {
					f1.setFollowState(true);
				}
			}
		}
		
		model.addAttribute("followers", followers);
		return "follow/follower";
	}
	
	@GetMapping("/follow/following/{id}")
	public String followFollowing(
			@PathVariable int id, 
			@AuthenticationPrincipal MyUserDetail userDetail, 
			Model model) 
	{
		// 대상의 팔로잉 리스트
		List<Follow> follows = followRepository.findByFromUserId(id);
		
		// 당사자의 팔로잉 리스트
		List<Follow> principalFollows = followRepository.findByFromUserId(userDetail.getUser().getId());
		
		for(Follow f1 : follows) {
			for(Follow f2 : principalFollows) {
				if(f1.getToUser().getId() == (f2.getToUser().getId())) {
					f1.setFollowState(true);
				}
			}
		}
		
		model.addAttribute("follows", follows);
		return "follow/follow";
	}
	
}
