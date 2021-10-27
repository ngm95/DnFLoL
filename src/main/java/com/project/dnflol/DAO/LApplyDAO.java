package com.project.dnflol.DAO;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.project.dnflol.DTO.LApplyDTO;

@Repository
public class LApplyDAO {
	private static final String MAPPER = "LApplyMapper";

	@Autowired
	SqlSessionTemplate template;

	public void create(LApplyDTO lapplyDto) {
		template.insert(MAPPER + ".create", lapplyDto);
	}

	public LApplyDTO read(LApplyDTO lapplyDto) {
		return template.selectOne(MAPPER + ".read", lapplyDto);
	}

	public List<LApplyDTO> readAllByGroupId(int groupId) {
		return template.selectList(MAPPER + ".readAllByGroupId", groupId);
	}
	
	public void updateLapplyResult(LApplyDTO lapplyDto) {
		template.update(MAPPER + ".updatelapplyResult", lapplyDto);
	}
	
	public void delete(LApplyDTO lapplyDto) {
		template.delete(MAPPER + ".delete", lapplyDto);
	}
}
