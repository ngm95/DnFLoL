package com.project.dnflol.DAO;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.project.dnflol.DTO.DApplyDTO;

@Repository
public class DApplyDAO {
	private static final String MAPPER = "DApplyMapper";

	@Autowired
	SqlSessionTemplate template;

	public void create(DApplyDTO dapplyDto) {
		template.insert(MAPPER + ".create", dapplyDto);
	}

	public DApplyDTO read(DApplyDTO dapplyDto) {
		return template.selectOne(MAPPER + ".read", dapplyDto);
	}

	public List<DApplyDTO> readAllByGroupId(int groupId) {
		return template.selectList(MAPPER + ".readAllByGroupId", groupId);
	}
	
	public List<DApplyDTO> readAllMyApply(String uid) {
		return template.selectList(MAPPER + ".readAllMyApply", uid);
	}
	
	public void updateDapplyResult(DApplyDTO dapplyDto) {
		template.update(MAPPER + ".updateDapplyResult", dapplyDto);
	}
	
	public void delete(DApplyDTO dapplyDto) {
		template.delete(MAPPER + ".delete", dapplyDto);
	}
}
