package com.project.dnflol.DTO;

import lombok.Data;

@Data
public class DCharDTO {
	String uid;
	String dcharId;
	String dcname;
	String dcserver;
	
	 public DCharDTO(String uid, String dcname, String dcharId, String dcserver) {
	        this.uid = uid;
	        this.dcname = dcname;
	        this.dcharId = dcharId;
	        this.dcserver = dcserver;
	    }
}