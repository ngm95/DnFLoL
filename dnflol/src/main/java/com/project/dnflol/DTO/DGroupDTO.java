package com.project.dnflol.DTO;
import java.sql.Date;

import lombok.Data;

@Data
public class DGroupDTO {
	int dgroupId;
	String dgroupOwner;
	int dgroupType;
	int dgroupMax;
	String dgroupName;
	String dgroupDate;
	String dgroupDetail;
	String dgroupOwnerName;
}

