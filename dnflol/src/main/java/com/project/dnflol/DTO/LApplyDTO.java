package com.project.dnflol.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LApplyDTO {
	int lapplyId;
	String lcharName;
	int lgroupId;
	String lgroupName;
	String lapplyResult;
	
	public LApplyDTO(int lapplyId, int lgroupId, String lapplyResult) {
		this.lapplyId = lapplyId;
		this.lgroupId = lgroupId;
		this.lapplyResult = lapplyResult;
	}
}
