package com.project.dnflol.DAO;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.project.dnflol.DTO.LCharDTO;

@Repository
public class LCharDAO {
	private static final String MAPPER = "LCharMapper";
	
	@Autowired
	SqlSessionTemplate template;
	
	public void create(LCharDTO lcharDto) {
		template.insert(MAPPER + ".create", lcharDto);
	}
	
	public LCharDTO readById(int lcharId) {
		return template.selectOne(MAPPER + ".readById", lcharId);
	}
	
	public LCharDTO readByName(String lcharName) {
		return template.selectOne(MAPPER + ".readByName", lcharName);
	}
	
	public List<LCharDTO> readAllByUid(String uid) {
		return template.selectList(MAPPER + ".readAllByUId", uid);
	}
	
	public List<LCharDTO> readAllAcceptedByGroupId(int groupId) {
		return template.selectList(MAPPER + ".readAllAcceptedByGruopId", groupId);
	}
	
	public void deleteById(String lcharId) {
		template.delete(MAPPER + ".deleteById", lcharId);
	}
	
	public void deleteByName(String lcharName) {
		template.delete(MAPPER + ".deleteByName", lcharName);
	}
}
