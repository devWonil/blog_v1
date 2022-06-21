package com.tencoding.blog.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tencoding.blog.model.User;
import com.tencoding.blog.repository.UserRepository;

@Service // (IoC 등록)
public class UserService {
	
	/*
	 * 서비스가 필요한 이유
	 * 현실 모델링 : 레스토랑 (주문 - 종업원 - 요리사)
	 * 트랜잭션 관리
	 * 송금 : 홍길동, 이순신
	 * 홍길동(100,000), 이순신(0) --> 홍길동(select) --> 이순신(50,000) insert
	 * 
	 * 하나의 기능 + 하나의 기능을 묶어서 단위의 기능을 만들어 내기 위해 필요하다.
	 * 하나의 기능 (서비스가 될 수 있다)
	 */

	// DI 의존성 주입
	@Autowired // 자동초기화 nullPointerException 방지
	private UserRepository userRepository;
	
	@Transactional
	public int saveUser(User user) {
		try {
			userRepository.save(user);
			return 1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
}
