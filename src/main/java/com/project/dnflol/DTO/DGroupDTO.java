package com.project.dnflol.DTO;
import java.sql.Date;

import lombok.Data;

@Data
public class DGroupDTO {
	int dgroupId;
	int dgroupOwner;
	int dgroupType;
	int dgroupMax;
	String dgroupName;
	Date dgroupDate;
	String dgroupDetail;
}

