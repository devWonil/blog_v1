package com.tencoding.blog.test;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncTest {

	@Test
	public void hashEncode() {
		String encPassword = new BCryptPasswordEncoder().encode("123");
		System.out.println("해시 값 : " + encPassword);
	}
	
	// classPath jUnit5 연결하기!!
	// 1. 해시 값 : $2a$10$nlnjyJTOwN0.TtnztLLv1up5DsEIv.igEWIkSTlLXzMW1mNTJw0IW
	// 2. 해시 값 : $2a$10$9Bdk2wBMtn0cIx4m6f0zpuu7oYpBWHki5jg/udn2HiKo.NRkJxDlq
}
