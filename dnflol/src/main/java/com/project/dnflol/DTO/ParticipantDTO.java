package com.project.dnflol.DTO;

import lombok.Data;

@Data
public class ParticipantDTO {
	String gameType;
	int assists;
	int champExperience;
	int champLevel;
	String championName;
	int damageDealtToTurrets;
	int deaths;
	int goldEarned;
	int goldSpent;
	String individualPosition;
	int kills;
	int summonerLevel;
	String summonerName;
	String teamPosition;
	int totalDamageDealtToChampions;
	int totalDamageTaken;
	int trueDamageDealtToChampions;
	int visionScore;
}
