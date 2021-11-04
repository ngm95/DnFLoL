package com.project.dnflol.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LApplyDTO {
	int lapplyId;
	String lcharName;
	int lgroupId;
	String lapplyResult;
	public LApplyDTO(String lcharName, int lgroupId) {
		this.lcharName = lcharName;
		this.lgroupId = lgroupId;
	}
}
