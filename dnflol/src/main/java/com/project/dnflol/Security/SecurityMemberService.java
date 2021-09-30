package com.project.dnflol.Security;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SecurityMemberService implements UserDetailsService {
	private SecurityRepository sRep;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<SecurityMemberEntity> optionalEntity = sRep.findByEmail(username);
		SecurityMemberEntity entity = optionalEntity.orElse(null);
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(entity.getRoleKey()));
		
		authorities.add(new SimpleGrantedAuthority(Role.MEMBER.getKey()));
		return new User(entity.getId(), entity.getPw(), authorities);
	}
	
	public SecurityMemberEntity signUpMember(SecurityMemberDTO securityMemberDto) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		securityMemberDto.setPw(bCryptPasswordEncoder.encode(securityMemberDto.getPw()));
		return sRep.save(securityMemberDto.toEntity());
	}
}
