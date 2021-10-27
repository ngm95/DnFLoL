package com.project.dnflol.DTO;

import lombok.Data;

@Data
public class LApplyDTO {
	int lapplyId;
	String lcharName;
	int lgroupId;
	String lapplyResult;
	
	public LApplyDTO() {
		
	}
	public LApplyDTO(String lcharName, int lgroupId) {
		this.lcharName = lcharName;
		this.lgroupId = lgroupId;
	}
}
