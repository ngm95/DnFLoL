package com.project.dnflol.Security;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.AllArgsConstructor;

@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	private SecurityMemberService smServ;
	
	@Override
	public void configure(WebSecurity web) {
		web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.csrf().disable()								// csrf 미사용
				.authorizeRequests()
				.antMatchers("/").permitAll()					// "/"는 모두 접근 가능
				.antMatchers("/register/**").permitAll()		// "/register" 하위의 url은 모든 사람이 접근 가능
				.antMatchers("/admin/**").hasRole("ADMIN")		// "/admin" 하위의 url은 admin만 접근 가능
				.anyRequest().hasRole("MEMBER")					// 그 이외 url은 member만 접근 가능
			.and()
				.formLogin()									// 로그인은 form 기반으로 HttpSession을 이용함
				.loginPage("/user/login")						// "/user/login"이 로그인 페이지
				.successForwardUrl("/")							// 로그인에 성공하면 "/"로 이동
				.failureForwardUrl("/")							// 로그인에 실패하면 "/"로 이동
				.permitAll()									// 이 페이지엔 모든 사람이 접근 가능
			.and()
				.logout()										// 로그아웃 설정
				.logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))	// "/user/logout"에서 로그아웃 실시
				.logoutSuccessUrl("/")							// 로그아웃에 성공하면 "/"로 이동
				.invalidateHttpSession(true)					// 세션을 초기화함
			.and()
				.exceptionHandling()							// 에러를 처리함
				.accessDeniedPage("/error");					// 에러가 발생하면 "/error"로 이동	
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder builder) throws Exception {
		builder.userDetailsService(smServ).passwordEncoder(bCryptPasswordEncoder());
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
