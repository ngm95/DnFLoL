package com.project.dnflol.DTO;

import java.util.List;

import lombok.Data;

@Data
public class InfoDTO {
	long gameCreation;
	long gameDuration;
	long gameStartTimestamp;
	long gameEndTimestamp;
	int queueId;
	List<ParticipantDTO> participants;
	ParticipantDTO myInfo;
	
	public void setMyInfoByLcharName(String lcharName) {
		for (ParticipantDTO participant : participants) {
			if (participant.getSummonerName().equals(lcharName)) {
				this.myInfo = participant;
				return;
			}
		}
	}
}
