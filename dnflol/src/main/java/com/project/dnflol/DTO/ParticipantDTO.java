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
	String individualPosition;
	int kills;
	int summonerLevel;
	String summonerName;
	String teamPosition;
	int totalDamageDealtToChampions;
	int totalDamageTaken;
	int trueDamageDealtToChampions;
	int visionScore;
	int sightWardsBoughtInGame;
	boolean win;
	int item0;
	int item1;
	int item2;
	int item3;
	int item4;
	int item5;
	int item6;
	int totalMinionsKilled;
	int neutralMinionsKilled;
	PerksDTO perks;
	int summoner1Id;
	int summoner2Id;
}
