package com.project.dnflol.Security;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.project.dnflol.Exception.BadCredentailsException;

public class CumsomAuthenticationProvider implements AuthenticationProvider {
	
	@Autowired
	private CustomUserDetailsService cudServ;
	
	@Autowired
	private BCryptPasswordEncoder encoder;

	@Override
	public Authentication authenticate(Authentication auth) throws AuthenticationException {
		String uid = auth.getName();
		String pw = (String)auth.getCredentials();
		
		CustomUserDetails user = (CustomUserDetails)cudServ.loadUserByUsername(uid);
		
		Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>)user.getAuthorities();

		if (!encoder.matches(pw, user.getPassword()))
			throw new BadCredentailsException("비밀번호가 일치하지 않습니다.");
		
		return new UsernamePasswordAuthenticationToken(uid, user.getPassword(), authorities);
		
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}
}
