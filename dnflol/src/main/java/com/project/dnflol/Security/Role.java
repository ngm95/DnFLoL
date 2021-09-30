package com.project.dnflol.Security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Role {
	ADMIN("ROLE_ADMIN", "ADMIN"),
	MEMBER("ROLE_MEMBER", "MEMBER");
	
	private String key;
	private String title;
}
