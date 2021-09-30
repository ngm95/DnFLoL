package com.project.dnflol.Security;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SecurityService implements UserDetailsService {
	private SecurityRepository securityRepository;
}
