package com.project.dnflol.DTO;

import lombok.Data;

@Data
public class LeagueDTO {
	String leagueId;
	String queueType;
	String tier;
	String rank;
	String summonerId;
	String summonerName;
	String leaguePoints;
	String wins;
	String losses;
	String veteran;
	String inactive;
	String freshBlood;
	String hotStreak;
}
