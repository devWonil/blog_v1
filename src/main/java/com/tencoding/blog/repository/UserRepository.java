package com.tencoding.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tencoding.blog.model.User;

//DAO 역할을 한다
// Bean으로 등록 될 수 있나요? --> 스프링에서 IoC에서 객체를 가지고 있나요?
public interface UserRepository extends JpaRepository<User, Integer>{
	// select
	// insert
	// update
	// delete
	
	// spring JPA 네이밍 전략
	// SELECT * FROM user WHERE username = ?1 AND password = ?2;
//	User findByUsernameAndPassword(String username, String password);
	
	// 두번째 방법
//	@Query(value = "SELECT * FROM user WHERE username = ?1 AND password = ?2;")
//	User 
	
	// SELECT * FROM user WHERE username = 1?;
	Optional<User> findByUsername(String username);
}
