package com.project.dnflol.DTO;

import lombok.Data;

@Data
public class DApplyDTO {
	int dapplyId;
	String dcharId;
	int dgroupId;
	String dapplyResult;
	
	public DApplyDTO(String dcharId, int dgroupId) {
		this.dcharId = dcharId;
		this.dgroupId = dgroupId;
	}
}