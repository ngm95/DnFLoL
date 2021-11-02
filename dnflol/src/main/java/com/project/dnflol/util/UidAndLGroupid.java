package com.project.dnflol.util;

import lombok.Data;

@Data
public class UidAndLGroupid {
	String uid;
	int lgroupId;
	
	public UidAndLGroupid(String uid, int lgroupId) {
		this.uid = uid;
		this.lgroupId = lgroupId;
	}
}
