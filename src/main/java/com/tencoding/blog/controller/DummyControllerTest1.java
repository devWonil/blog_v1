//package com.tencoding.blog.controller;
//
//import java.util.List;
//
//import javax.transaction.Transactional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort.Direction;
//import org.springframework.data.web.PageableDefault;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.tencoding.blog.model.RoleType;
//import com.tencoding.blog.model.User;
//import com.tencoding.blog.repository.UserRepository;
//
//@RestController
//public class DummyControllerTest1 {
//
//	@Autowired
//	private UserRepository userRepository; // DI
//	
//	@GetMapping("/dummy/users")
//	public List<User> 전체사용자검색() {
//		return userRepository.findAll();
//	}
//	
//	// 페이징 처리
//	// http://localhost:9090/blog/dummy/user?page=0 (Query-Parameter 방식)
//	@GetMapping("/dummy/user")
//	public Page<User> pageList(@PageableDefault(size = 10, sort = "id", direction = Direction.DESC) Pageable pageable){
//		
//		Page<User> pageUser = userRepository.findAll(pageable);
//		return pageUser;
//	}
//	
//	// Rest API (POST)
//	@PostMapping("/dummy/join")
//	public String join(@RequestBody User user) {
//		System.out.println("------------------");
//		System.out.println(user.getUsername());
//		System.out.println(user.getPassword());
//		System.out.println(user.getEmail());
//		System.out.println("------------------");
//		
//		System.out.println(user.getId());
//		System.out.println(user.getCreateDate());
//		System.out.println(user.getRole()); // null -> default 값 불가
//		
//		user.setRole(RoleType.USER);
//		userRepository.save(user);
//		
//		return "회원가입 완료되었습니다";
//	}
//	
//	@GetMapping("dummy/user/{id}")
//	public User detail(@PathVariable int id) {
//		User user = userRepository.findById(id).orElseThrow(() -> {
//			return new IllegalArgumentException("해당 유저는 없는 사람입니다");
//		});
//		
//		return user;
//	}
//	
//	@Transactional
//	@PutMapping("/dummy/user/{id}")
//	public User updateUser(@PathVariable int id, @RequestBody User reqUser) {
//		System.out.println("id : " + id);
//		System.out.println("password : " + reqUser.getPassword());
//		System.out.println("email : " + reqUser.getEmail());
//		// select 해와서 추가적인 처리
//		User userEntity = userRepository.findById(id).orElseThrow(() -> {
//			return new IllegalArgumentException("해당 유저는 없는 사람입니다");
//		});
//		
//		userEntity.setPassword(reqUser.getPassword());
//		userEntity.setEmail(reqUser.getEmail());
//		
//		return null;
//	}
//	
//	@DeleteMapping("dummy/user/{id}")
//	public String delete(@PathVariable int id) {
//		
//		try {
//			userRepository.deleteById(id);
//		}catch (Exception e) {
//			return "해당 유저는 없습니다";
//		}
//		
//		return id + "는 삭제되었습니다";
//	}
//}
