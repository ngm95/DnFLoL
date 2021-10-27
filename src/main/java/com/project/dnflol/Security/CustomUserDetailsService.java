package com.project.dnflol.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
	private UserAuthDAO userAuthDao;
	
	@Override
	public UserDetails loadUserByUsername(String uid) throws UsernameNotFoundException {
		CustomUserDetails user = userAuthDao.getUserById(uid);
		
		if (user == null)
			throw new InternalAuthenticationServiceException(uid);
		
		return user;
	}
}
