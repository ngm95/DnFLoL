package com.project.dnflol.DTO;

import lombok.Data;

@Data
public class LApplyDTO {
	int lapplyId;
	String lcharId;
	int lgroupId;
	String lapplyResult;
	
	public LApplyDTO(String lcharId, int lgroupId) {
		this.lcharId = lcharId;
		this.lgroupId = lgroupId;
	}
}
