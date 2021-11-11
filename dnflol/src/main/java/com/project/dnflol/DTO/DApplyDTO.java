package com.project.dnflol.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DApplyDTO {
	int dapplyId;
	String dcharId;
	String dcname;
	int dgroupId;
	String dgroupName;
	String dapplyResult;
	
	public DApplyDTO(int dapplyId, int dgroupId, String dapplyResult) {
		this.dapplyId = dapplyId;
		this.dgroupId = dgroupId;
		this.dapplyResult = dapplyResult;
	}
	
	public DApplyDTO(String dcharId, String dcname, int dgroupId, String dgroupName) {
		this.dcharId = dcharId;
		this.dcname = dcname;
		this.dgroupId = dgroupId;
		this.dgroupName = dgroupName;
	}
}