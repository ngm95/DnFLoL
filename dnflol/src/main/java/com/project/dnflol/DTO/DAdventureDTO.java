package com.project.dnflol.DTO;
import lombok.Data;

@Data
public class DAdventureDTO {
	String serverId; //서버아이디
	String characterName;//캐릭터 고유 코드
	String characterId;
	String code; //타임라인 코드 201 : 레이드, 
	String next; //다음 데이터 조회
	int limit; // 변환 Row수 기본:10, 최대:100
	
	String[] server_dict={"all","anton","bakal","cain","casillas", "diregie","hilder","prey","siroco"};
    Ddate date; //날씨
	@Data
	private class Ddate {
		String startDate; //예시 20210708T0000
		String endDate;
		
		public String toString() {
			return "startDate="+startDate + "&endDate=" + endDate;
		}
	}
}
