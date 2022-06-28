package com.tencoding.blog.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tencoding.blog.dto.KakaoProfile;
import com.tencoding.blog.dto.OAuthToken;
import com.tencoding.blog.dto.ResponseDto;
import com.tencoding.blog.model.User;
import com.tencoding.blog.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private HttpSession httpSession;
	
	@Autowired
	private UserService userService;
	
	// .../blog/user/login_form
	@GetMapping("/auth/login_form")
	public String loginForm() {
		return "user/login_form";
	}
	
	@GetMapping("/auth/join_form")
	public String joinForm() {
		return "user/join_form";
	}
	
	@GetMapping("/logout")
	public String logout() {
		// 세션정보를 제거 (로그아웃처리) 
		httpSession.invalidate();
		return "redirect:/";
	}

	@GetMapping("/user/update_form")
	public String updateForm() {
		return "user/update_form";
	}
	
	@PostMapping("/auth/joinProc")
	public String save(User user){
		int result = userService.saveUser(user);
		return "redirect:/";
	}
	
	
	@GetMapping("/auth/kakao/callback")
	@ResponseBody
	public String kakaoCallback(@RequestParam String code) {
		// HTTPURLConnect ...
		// Retrofit2
		// OkHttp
		// RestTemplate
		RestTemplate rt = new RestTemplate();
		
		// http 메시지 -> POST
		
		// 시작줄
		// http header
		// http body
		
		// header 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		// body 생성
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		
		params.add("grant_type", "authorization_code");
		params.add("client_id", "1e0d85577dad20bb9104174f24adbfb7");
		params.add("redirect_uri", "http://localhost:9090/auth/kakao/callback");
		params.add("code", code);
		
		// header와 body를 하나의 object로 담아야한다.
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);
		
		// 준비 끝 Http 요청 - post 방식 - 응답
		ResponseEntity<String> response = 
				rt.exchange("https://kauth.kakao.com/oauth/token", 
						HttpMethod.POST, kakaoTokenRequest, String.class);
		
		// response -> object 타입으로 변환 (Gson, Json Simple, ObjectMapper)
		OAuthToken authToken = null;
		
		ObjectMapper objectMapper = new ObjectMapper();
		// String --> Object (클래스 생성)
		try {
			authToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		// 액세스 토큰 사용
		RestTemplate rt2 = new RestTemplate();
		
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization", "Bearer " + authToken.getAccessToken()); // Bearer 무조건 한 칸 띄움
		headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8 ");		
		
		// body
		HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers2);
		
		ResponseEntity<String> response2 = rt2.exchange("https://kapi.kakao.com/v2/user/me", 
				HttpMethod.POST, kakaoProfileRequest, String.class);
		
		//System.out.println(response2);
		
		// data parsing 하기 (KakaoProfile.class)
		KakaoProfile kakaoProfile = null;
		
		ObjectMapper objectMapper2 = new ObjectMapper();
		
		try {
			kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// POST -->
		// 통신 -- 인증서버
		return "카카오 프로필 정보 요청 : " + kakaoProfile;
	}
	
	
}