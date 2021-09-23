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
	int leaguePoints;
	int wins;
	int losses;
	boolean veteran;
	boolean inactive;
	boolean freshBlood;
	boolean hotStreak;
}
