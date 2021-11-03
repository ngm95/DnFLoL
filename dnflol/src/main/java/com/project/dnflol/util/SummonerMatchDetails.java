package com.project.dnflol.util;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SummonerMatchDetails {
	int totalGames;
	int topGames;
	int jugGames;
	int midGames;
	int botGames;
	int supGames;
	
	int totalKills;
	int topKills;
	int jugKills;
	int midKills;
	int botKills;
	int supKills;
	int totalAssists;
	int topAssists;
	int jugAssists;
	int midAssists;
	int botAssists;
	int supAssists;
	int totalDeaths;
	int topDeaths;
	int jugDeaths;
	int midDeaths;
	int botDeaths;
	int supDeaths;
	
	int totalDamageToChampion;
	int topDamageToChampion;
	int jugDamageToChampion;
	int midDamageToChampion;
	int botDamageToChampion;
	int supDamageToChampion;
	int totalDamageTaken;
	int topDamageTaken;
	int jugDamageTaken;
	int midDamageTaken;
	int botDamageTaken;
	int supDamageTaken;
	
	public void increaseTopCount() {
		this.topGames += 1;
	}
	
	public void increaseJugCount() {
		this.jugGames += 1;
	}
	
	public void increaseMidCount() {
		this.midGames += 1;
	}
	
	public void increaseBotCount() {
		this.botGames += 1;
	}
	
	public void increaseSupCount() {
		this.supGames += 1;
	}
	
	public void addTotalKDA(int kills, int deaths, int assists) {
		this.totalKills += kills;
		this.totalAssists += assists;
		this.totalDeaths += deaths;
	}
	
	public void addTopKDA(int kills, int deaths, int assists) {
		this.topKills += kills;
		this.topAssists += assists;
		this.topDeaths += deaths;
	}
	
	public void addJugKDA(int kills, int deaths, int assists) {
		this.jugKills += kills;
		this.jugAssists += assists;
		this.jugDeaths += deaths;
	}
	
	public void addMidKDA(int kills, int deaths, int assists) {
		this.midKills += kills;
		this.midAssists += assists;
		this.midDeaths += deaths;
	}
	
	public void addBotKDA(int kills, int deaths, int assists) {
		this.botKills += kills;
		this.botAssists += assists;
		this.botDeaths += deaths;
	}
	
	public void addSupKDA(int kills, int deaths, int assists) {
		this.supKills += kills;
		this.supAssists += assists;
		this.supDeaths += deaths;
	}
	
	public void addTotalDamage(int dealt, int taken) {
		this.totalDamageToChampion += dealt;
		this.totalDamageTaken += taken;
	}
	
	public void addTopDamage(int dealt, int taken) {
		this.topDamageToChampion += dealt;
		this.topDamageTaken += taken;
	}
	
	public void addJugDamage(int dealt, int taken) {
		this.jugDamageToChampion += dealt;
		this.jugDamageTaken += taken;
	}
	
	public void addMidDamage(int dealt, int taken) {
		this.midDamageToChampion += dealt;
		this.midDamageTaken += taken;
	}
	
	public void addBotDamage(int dealt, int taken) {
		this.botDamageToChampion += dealt;
		this.botDamageTaken += taken;
	}
	
	public void addSupDamage(int dealt, int taken) {
		this.supDamageToChampion += dealt;
		this.supDamageTaken += taken;
	}
}
