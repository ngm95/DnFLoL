package com.project.dnflol.DTO;

import lombok.Data;

@Data
public class LCharDTO {
	int lcharId;
	String uid;
	String lcharName;
	public LCharDTO(String uid, String lcharName) {
		this.uid = uid;
		this.lcharName = lcharName;
	}
}
