package com.project.dnflol.DTO;

import lombok.Data;

@Data
public class LApplyDTO {
	int lapplyId;
	int lcharId;
	int lgroupId;
	String lapplyResult;
	
	public LApplyDTO(int lcharId, int lgroupId) {
		this.lcharId = lcharId;
		this.lgroupId = lgroupId;
	}
}
