package com.project.dnflol.DTO;

import lombok.Data;

@Data
public class ParticipantDTO {
	int assists;
	int champExperience;
	int champLevel;
	String championName;
	int damageDealtToTurrets;
	int deaths;
	boolean firstBloodKill;
	boolean firstTowerKill;
	int goldEarned;
	int goldSpent;
	String individualPosition;
	int kills;
	String lane;
	int sightWardsBoughtInGame;
	int summonerLevel;
	String summonerName;
	String teamPosition;
	int totalDamageDealtToChampions;
	int totalDamageTaken;
	int totalMinionKilled;
	int trueDamageDealtToChampions;
	int visionScore;
}
