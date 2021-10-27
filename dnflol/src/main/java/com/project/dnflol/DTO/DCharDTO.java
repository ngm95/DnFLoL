package com.project.dnflol.DTO;

import lombok.Data;

@Data
public class DCharDTO {
	String uid;
	String dcharId;
	String dcname;
	String dcserver;
	public DCharDTO(String uid, String dcname) {
		this.uid = uid;
		this.dcname = dcname;
	}
	}