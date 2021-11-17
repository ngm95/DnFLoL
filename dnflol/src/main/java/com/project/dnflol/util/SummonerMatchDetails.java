package com.project.dnflol.util;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SummonerMatchDetails {
	int totalGames;
	
	int totalKills;
	int totalAssists;
	int totalDeaths;
	
	int totalDamageToChampion;
	int totalDamageTaken;
	
	public void addTotalKills(int kills) {
		this.totalKills += kills;
	}
	public void addTotalAssists(int assists) {
		this.totalAssists += assists;
	}
	public void addTotalDeaths(int deaths) {
		this.totalDeaths += deaths;
	}
	
	public void addTotalDamageToChampion(int damage) {
		this.totalDamageToChampion += damage;
	}
	public void addTotalDamageTaken(int damage) {
		this.totalDamageTaken += damage;
	}
}
