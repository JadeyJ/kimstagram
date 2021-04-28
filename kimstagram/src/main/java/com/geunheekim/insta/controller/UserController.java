package com.geunheekim.insta.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.geunheekim.insta.model.User;
import com.geunheekim.insta.repository.FollowRepository;
import com.geunheekim.insta.repository.UserRepository;
import com.geunheekim.insta.service.MyUserDetail;

@Controller
public class UserController {
	
	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private FollowRepository followRepository;
	
	@GetMapping("/auth/login")
	public String authLogin() {
		return "auth/login";
	}
	
	@GetMapping("/auth/join")
	public String authJoin() {
		return "auth/join";
	}
	
	@PostMapping("/auth/joinProc")
	public String authJoinProc(User user) {
		String rawPassword = user.getPassword();
		String encPassword = encoder.encode(rawPassword);
		user.setPassword(encPassword);
		log.info("rawPassword : " + rawPassword);
		log.info("encPassword : " + encPassword);
		
		userRepository.save(user);
		
		return "redirect:/auth/login";
	}
	
	@GetMapping("/user/{id}")
	public String profile(
			@PathVariable int id, @AuthenticationPrincipal MyUserDetail userDetail, Model model) 
	{
		// id를 통해서 해당 유저를 검색(이미지 + 정보)
		userRepository.findById(id);
		
		/**
		 * 1. imageCount
		 * 2. followerCount
		 * 3. followingCount
		 * 4. User오브젝트(Image (likeCount) 컬렉션)
		 * 5. followCheck 팔로우 유무 - 1 팔로우, 1이 아니면 언팔로우
		 */
		
		// 4번 - Temp
		Optional<User> oToUser = userRepository.findById(id);
		User user = oToUser.get();
		model.addAttribute("user", user);
		
		
		// 5번
		User principal = userDetail.getUser();
		
		int followCheck = followRepository.countByFromUserIdAndToUserId(principal.getId(), id);
		log.info("followCheck : " + followCheck);
		model.addAttribute("followCheck", followCheck);
		
		return "user/profile";
	}
	
	@GetMapping("/user/edit/{id}")
	public String userEdit(@PathVariable int id) {
		// 해당 id로 select 하기
		// findByUserInfo() 사용 - 만들어야 함
		
		return "user/edit-profile";
	}
}
