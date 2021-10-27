package com.project.dnflol.DAO;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.project.dnflol.DTO.DCharDTO;

@Repository
public class DCharDAO {
	private static final String MAPPER = "DCharMapper";
	
	@Autowired
	SqlSessionTemplate template;
	
	public void create(DCharDTO dcharDto) {
		template.insert(MAPPER + ".create", dcharDto);
	}
	
	public DCharDTO readByName(String dcharName) {
		return template.selectOne(MAPPER + ".readByName", dcharName);
	}
	public DCharDTO readById(String dcharId) {
		return template.selectOne(MAPPER + ".readById", dcharId);
	}
	
	public List<DCharDTO> readAllByUid(String uid) {
		return template.selectList(MAPPER + ".readAllByUId", uid);
	}
	
	public List<DCharDTO> readAllAcceptedByGroupId(int groupId) {
		return template.selectList(MAPPER + ".readAllAcceptedByGruopId", groupId);
	}
	
	public void deleteByName(String dcharName) {
		template.delete(MAPPER + ".deleteByName", dcharName);
	}
	public void deleteById(String dcharId) {
		template.delete(MAPPER + ".deleteById", dcharId);
	}
}
