package com.project.dnflol.DTO;

import java.util.List;

import lombok.Data;

@Data
public class PerkStyleDTO {
	int style;
	List<PerkStyleSelectionDTO> selections;
}
