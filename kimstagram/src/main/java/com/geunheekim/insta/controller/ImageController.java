package com.geunheekim.insta.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.geunheekim.insta.model.Image;
import com.geunheekim.insta.model.Tag;
import com.geunheekim.insta.model.User;
import com.geunheekim.insta.repository.ImageRepository;
import com.geunheekim.insta.repository.TagRepository;
import com.geunheekim.insta.service.MyUserDetail;
import com.geunheekim.insta.util.Utils;

@Controller
public class ImageController {
	
	private static final Logger log = LoggerFactory.getLogger(ImageController.class);

	@Autowired
	private ImageRepository imageRepository;
	
	@Autowired
	private TagRepository tagRepository;
	
	@Value("${file.path}")
	private String fileRealPath;
	
	@GetMapping({"/", "/image/feed"})
	public String imageFeed(@AuthenticationPrincipal MyUserDetail userDetail) {
		log.info("username : " + userDetail.getUsername());
		
		// 나 : 1번) 내가 팔로우한 친구들의 사진
		// select * from image where userId in (select toUserId from follow where fromUserId = 1)
		
		
		
		
		
		return "image/feed";
	}
	
	@GetMapping("/image/upload")
	public String imageUpload() {
		return "image/upload-image";
	}
	
	@PostMapping("/image/uploadProc")
	public String imageUploadProc(
		@AuthenticationPrincipal MyUserDetail userDetail,
		@RequestParam("file") MultipartFile file,
		@RequestParam("caption") String caption,
		@RequestParam("location") String location,
		@RequestParam("tags") String tags
	) 
	{
		// 이미지 업로드 수행
		UUID uuid = UUID.randomUUID();
		String uuidFilename = uuid + "_" + file.getOriginalFilename();
		
		Path filePath = Paths.get(fileRealPath + uuidFilename);
		
		try {
			// 하드디스크 기록
			Files.write(filePath, file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		User principal = userDetail.getUser();
		
		Image image = new Image();
		image.setCaption(caption);
		image.setLocation(location);
		image.setUser(principal);
		image.setPostImage(uuidFilename);
		
		// <img src="/upload/파일명" />
		
		imageRepository.save(image);
		
		// Tag 객체 생성 
		List<String> tagList = Utils.tagParser(tags);
		
		for(String tag : tagList) {
			Tag t = new Tag();
			t.setName(tag);
			t.setImage(image);
			tagRepository.save(t);
			image.getTags().add(t);
		}
		
		return "redirect:/";
	}
	
}
