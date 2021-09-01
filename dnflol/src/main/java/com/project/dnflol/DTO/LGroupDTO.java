package com.project.dnflol.DTO;

import java.sql.Date;

import lombok.Data;

@Data
public class LGroupDTO {
	int lgroupID;
	int lgroupOwner;
	int lgrouptype;
	int lgroupMax;
	String lgroupName;
	Date lgroupDate;
	String lgroupDetail;
}
