package com.project.dnflol.DAO;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.project.dnflol.DTO.LCharDTO;

@Repository
public class LCharDAO {
	private static final String MAPPER = "LCharMapper";
	
	@Resource
	SqlSession sqlsession;
	
	public void create(LCharDTO lcharDto) {
		sqlsession.insert(MAPPER + ".create", lcharDto);
	}
	
	public LCharDTO readByName(String lcharName) {
		return sqlsession.selectOne(MAPPER + ".readByName", lcharName);
	}
	
	public void deleteById(String lcharId) {
		sqlsession.delete(MAPPER + ".deleteById", lcharId);
	}
	
	public void deleteByName(String lcharName) {
		sqlsession.delete(MAPPER + ".deleteByName", lcharName);
	}
}
