package com.project.dnflol.util;

import lombok.Data;

@Data
public class UidAndGroupId {
	String uid;
	int groupId;
	
	public UidAndGroupId(String uid, int groupId) {
		this.uid = uid;
		this.groupId = groupId;
	}
}
