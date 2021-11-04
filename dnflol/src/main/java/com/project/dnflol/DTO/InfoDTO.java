package com.project.dnflol.DTO;

import lombok.Data;

@Data
public class InfoDTO {
	long gameCreation;
	long gameDuration;
	int queueId;
	ParticipantDTO myInfo;
}
