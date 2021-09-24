package com.project.dnflol.DAO;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.project.dnflol.DTO.LGroupDTO;
import com.project.dnflol.util.BoardMinMax;

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
	
	public List<LGroupDTO> readAllByOwnerName(String onwerName) {
		return sqlsession.selectList(MAPPER + ".readAllByOwner", onwerName);
	}
	
	public List<LGroupDTO> readAllByDetail(String lgropDetail) {
		return sqlsession.selectList(MAPPER + ".readAllByDetail", lgropDetail);
	}
	
	public List<LGroupDTO> readAllByGroupName(String lgroupName) {
		return sqlsession.selectList(MAPPER + ".readAllByGroupName", lgroupName);
	}
	
	public List<LGroupDTO> readAllByUId(String uid) {
		return sqlsession.selectList(MAPPER + ".readAllByUId", uid);
	}
	
	public List<LGroupDTO> readLimitList(BoardMinMax bmm) {
		return sqlsession.selectList(MAPPER + ".readLimitList", bmm);
	}
	
	public int readMaxCount() {
		return sqlsession.selectOne(MAPPER + ".readMaxCount");
	}
	
	public void deleteById(int lgroupId) {
		sqlsession.delete(MAPPER + ".deleteById", lgroupId);
	}
}
