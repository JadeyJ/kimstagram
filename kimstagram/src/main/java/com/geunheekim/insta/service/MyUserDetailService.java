package com.geunheekim.insta.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.geunheekim.insta.model.User;
import com.geunheekim.insta.repository.UserRepository;

@Service
public class MyUserDetailService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		
		MyUserDetail userDetail = null;
		if(user != null) {
			userDetail = new MyUserDetail();
			userDetail.setUser(user);
		}else {
			throw new UsernameNotFoundException("'username' Not Found");
		}
		
		return userDetail;
	}
	
	
	
}
