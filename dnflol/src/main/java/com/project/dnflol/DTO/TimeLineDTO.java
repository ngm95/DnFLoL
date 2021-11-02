package com.project.dnflol.DTO;

import lombok.Data;

@Data
public class TimeLineDTO {
	int code;
	String name;
	String date;
	GameData data;
	@Data
	private class GameData {
		String itemId;
		String itemName;
		String raidName;
		String raidPartyName;
		String phaseName;
		String hard;
		
		public String toString() {
			return itemId + ", " + itemName;
		}
	}


}
