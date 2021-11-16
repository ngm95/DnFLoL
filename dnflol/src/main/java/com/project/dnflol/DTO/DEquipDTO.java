package com.project.dnflol.DTO;

import lombok.Data;

@Data
public class DEquipDTO {
	String slotId;
	String slotName;
	String itemId;
	String itemName;
	String itemType;
	String itemTypeDetail;
	String itemAvailableLevel;
	String itemRarity;
	String setItemId;
	String setItemName;
    int reinforce; //강화
    //transformInfo; //연옥 변환
    String itemGradeName; //아이템등급
    //enchant; //마부
    String amplificationName; //차원
    int refine; //재련
    //sirocoInfo; //잔향,시로코 옵션
    DEquipDTO upgradeInfo; //발려져있는 아이템
  
}
