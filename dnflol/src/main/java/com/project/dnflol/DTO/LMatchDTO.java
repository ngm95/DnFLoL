package com.project.dnflol.DTO;

import lombok.Data;

@Data
public class LMatchDTO {
	String platformId;
	long gameId;
	int champion;
	int season;
	long timestamp;
	String role;
	String lane;
}
