package com.tencoding.blog.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tencoding.blog.auth.PrincipalDetail;
import com.tencoding.blog.dto.RequestFileDto;
import com.tencoding.blog.model.Image;
import com.tencoding.blog.service.StoryService;

@Controller
@RequestMapping("/story")
public class StoryController {

	@Autowired
	StoryService storyService;
	
	@GetMapping("/home")
	public String storyHome(Model model, 
			@PageableDefault(size = 10, sort = "id", direction = Direction.DESC) Pageable pageable) {
		Page<Image> imagePage = storyService.getImageList(pageable);
		System.out.println("=================================");
		System.out.println(imagePage.getContent().toString());
		System.out.println("=================================");
		model.addAttribute("imagePage", imagePage);
		return "/story/home";
	}
	
	@GetMapping("/upload")
	public String storyUpload() {
		return "/story/upload";
	}
	
	@PostMapping("/image/upload")
//	public String storyImageUpload(MultipartFile file, String storyText) {
	public String storyImageUpload(RequestFileDto fileDto, @AuthenticationPrincipal PrincipalDetail principalDetail) {
		
		storyService.imageUpload(fileDto, principalDetail.getUser());
		// 1 byte -> 1000 byte == 1 KB -> 1,000,000 byte == 1 MB -> 1,000,000,000 byte == 1 GB -> 1,000,000,000,000 == 1 TB
		// 1 bit = 2진수 (0 과 1)
		// 1 byte = 8bit
		// 1 KB = 2의 10승 바이트, 1024 바이트
		// 컴퓨터는 1000배보다 1024배를 훨씬 빨리 계산한다
		// 컴퓨터는 2진수로 계산하는 것이 가장 편하고 빠르다
		return "redirect:/story/home";
	}
}
