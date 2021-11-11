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
	
	public void createToAccepted(DApplyDTO dapplyDto) {
		template.insert(MAPPER + ".createToAccepted", dapplyDto);
	}

	public DApplyDTO read(DApplyDTO dapplyDto) {
		return template.selectOne(MAPPER + ".read", dapplyDto);
	}
	
	public int readAcceptedCountByGroupId(int dgroupId) {
		return template.selectOne(MAPPER + ".readAcceptedCountByGroupId", dgroupId);
	}
	
	public List<DApplyDTO> readAllAcceptedByGroupId(int dgroupId) {
		return template.selectList(MAPPER + ".readAllAcceptedByGroupId", dgroupId);
	}

	public List<DApplyDTO> readAllByGroupId(int dgroupId) {
		return template.selectList(MAPPER + ".readAllByGroupId", dgroupId);
	}
	
	public List<DApplyDTO> readAllMyApply(String uid) {
		return template.selectList(MAPPER + ".readAllMyApply", uid);
	}
	
	public void updateLapplyResult(DApplyDTO dapplyDto) {
		template.update(MAPPER + ".updatedapplyResult", dapplyDto);
	}
	
	public void delete(DApplyDTO dapplyDto) {
		template.delete(MAPPER + ".delete", dapplyDto);
	}
}
