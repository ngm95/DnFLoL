package com.project.dnflol.DAO;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.project.dnflol.DTO.LGroupDTO;

@Repository
public class LGroupDAO {
	private static final String MAPPER = "LGroupMapper";
	
	@Resource
	SqlSession sqlsession;
	
	public void create(LGroupDTO lgroupDto) {
		sqlsession.insert(MAPPER + ".create", lgroupDto);
	}
	
	public LGroupDTO readById(int lgroupId) {
		return sqlsession.selectOne(MAPPER + ".readById", lgroupId); 
	}
	
	public List<LGroupDTO> readAllByOwner(int lgroupOnwer) {
		return sqlsession.selectList(MAPPER + ".readAllByOwner", lgroupOnwer);
	}
	
	public List<LGroupDTO> readAllByUId(String uid) {
		return sqlsession.selectList(MAPPER + ".readAllByUId", uid);
	}
	
	public void deleteById(int lgroupId) {
		sqlsession.delete(MAPPER + ".deleteById", lgroupId);
	}
}
