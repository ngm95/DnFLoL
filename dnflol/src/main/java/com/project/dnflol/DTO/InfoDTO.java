package com.project.dnflol.DTO;

import java.util.List;

import lombok.Data;

@Data
public class InfoDTO {
	long gameCreation;
	long gameDuration;
	int queueId;
	List<ParticipantDTO> participants;
	ParticipantDTO myInfo;
}
