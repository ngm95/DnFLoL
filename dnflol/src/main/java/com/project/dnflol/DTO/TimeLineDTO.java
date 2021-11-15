package com.project.dnflol.DTO;

import lombok.Data;
import com.project.dnflol.DTO.TimeLineDataDTO;

@Data
public class TimeLineDTO {
	int code;
	String name;
	String date;
	TimeLineDataDTO data;

}
