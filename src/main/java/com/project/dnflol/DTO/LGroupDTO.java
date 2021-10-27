package com.project.dnflol.DTO;

import java.sql.Date;

import lombok.Data;

@Data
public class LGroupDTO {
	int lgroupId;
	String lgroupOwner;	
	String lgroupType;	
	int lgroupMax;
	String lgroupName;
	Date lgroupDate;
	String lgroupDetail;
}
