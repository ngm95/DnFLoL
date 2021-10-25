package com.project.dnflol.DTO;

import lombok.Data;

@Data
public class LMatchDTO {
	String platformId;
	String gameId;
	String champion;
	String season;
	String timestamp;
	String role;
	String lane;
}
