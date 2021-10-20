package com.project.dnflol.DTO;

import java.sql.Date;

import lombok.Data;

@Data
public class LGroupDTO {
	int lgroupId;
	String lgroupOwner;	// int->String
	String lgroupType;	// int->String
	int lgroupMax;
	String lgroupName;
	Date lgroupDate;
	String lgroupDetail;
}
