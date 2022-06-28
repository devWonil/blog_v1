package com.tencoding.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.tencoding.blog.auth.PrincipalDetailService;

@Configuration // 빈 등록 (IoC)
@EnableWebSecurity // security filter로 등록이 된다 (필터 커스텀)
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정 주소로 접근하면 권한 및 인증처리를 미리 체크하겠다
public class SecurityConfig extends WebSecurityConfigurerAdapter{ // 커스텀하기위한 상속

	// 1. 비밀번호 해시 처리
	@Bean // IoC가 된다 (필요할 때 가져와서 사용하면 된다)
	public BCryptPasswordEncoder encoderPWD() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	@Override
		protected AuthenticationManager authenticationManager() throws Exception {
			return super.authenticationManager();
		}
	
	@Autowired
	private PrincipalDetailService principalDetailService;
	
	// 2. 특정 주소 필터를 설정할 예정
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
				.authorizeRequests()
				.antMatchers("/auth/**", "/", "/js/**", "/css/**", "/image/**", "/oauth/**")
				.permitAll()
				.anyRequest()
				.authenticated()
				.and().formLogin().loginPage("/auth/login_form")
				.loginProcessingUrl("/auth/loginProc")
				.defaultSuccessUrl("/"); // loginProc를 만들지 않음(스프링 시큐리티가 가로채서 진행)
		// 스프링 시큐리티가 해당 주소로 요청이 오면 가로채서 대신 로그인 처리를 해준다.
		// 단 이 동작을 완료하기 위해서는 만들어야 할 클래스가 있다.
		// UserDetails type Object를 만들어야 한다.
	}
	
	// 3 시큐리티가 대신 로그인을 해주는데 password를 가로채서 
	// 해당 패스워드가 무엇으로 해시 처리되었는지 함수를 알려줘야 한다.
	// 같은 해시로 암호화해서 DB의 해시값과 비교할 수 있다.
	@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			// 1. userDetailService 들어갈 Object를 만들어줘야 한다.
			// 2. passwordEncoder에 우리가 사용하는 해시 함수를 알려줘야한다.
			auth.userDetailsService(principalDetailService).passwordEncoder(encoderPWD()); // 이 한 줄 위해서 PrincipalDetail, principalDetailService, UserRepository
		}
}
