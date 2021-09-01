package com.project.dnflol.DAO;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.project.dnflol.DTO.LApplyDTO;

@Repository
public class LApplyDAO {
	private static final String MAPPER = "LApplyMapper";

	@Resource
	SqlSession sqlsession;

	public void create(LApplyDTO lapplyDto) {
		sqlsession.insert(MAPPER + ".create", lapplyDto);
	}

	public LApplyDTO read(LApplyDTO lapplyDto) {
		return sqlsession.selectOne(MAPPER + ".read", lapplyDto);
	}

	public List<LApplyDTO> readAllByGroupId(int groupId) {
		return sqlsession.selectList(MAPPER + ".readAllByGroupId", groupId);
	}
	
	public void updateLapplyResult(LApplyDTO lapplyDto) {
		sqlsession.update(MAPPER + ".updatelapplyResult", lapplyDto);
	}
	
	public void delete(LApplyDTO lapplyDto) {
		sqlsession.delete(MAPPER + ".delete", lapplyDto);
	}
}
