package com.project.dnflol.util;

import lombok.Data;

@Data
public class AuthInfo {
	private String uid;
	private String uname;
	public AuthInfo(String uid, String uname) {
		this.uid = uid;
		this.uname = uname;
	}
}
