package com.project.dnflol.DTO;


import lombok.Data;

@Data
public class DCharInforDTO {
	  String characterId;
	  String characterName;
	  int level;
	  String jobId;
	  String jobGrowId;
	  String jobName;
	  String jobGrowName;
	  String adventureName;
	  String guildId;
	  String guildName;
	  DEquipDTO equipment;
}
