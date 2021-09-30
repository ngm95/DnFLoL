package com.project.dnflol.Security;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class SecurityMemberDTO {
	private String id;
	private String name;
	private String email;
	private String pw;
	private Role role;
	
	public SecurityMemberEntity toEntity() {
		return SecurityMemberEntity.builder()
				.id(this.id)
				.name(this.name)
				.email(this.email)
				.pw(this.pw)
				.role(Role.MEMBER)
				.build();
	}
	
	@Builder
	public SecurityMemberDTO(String id, String name, String email, String pw, Role role) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.pw = pw;
		this.role = role;
	}
}
