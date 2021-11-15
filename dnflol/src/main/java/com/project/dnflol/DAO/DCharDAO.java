package com.project.dnflol.DAO;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.project.dnflol.DTO.DCharDTO;
import com.project.dnflol.util.UidAndGroupId;

@Repository
public class DCharDAO {
	private static final String MAPPER = "DCharMapper";
	
	@Autowired
	SqlSessionTemplate template;
	
	public void create(DCharDTO dcharDto) {
		template.insert(MAPPER + ".create", dcharDto);
	}
	
	public DCharDTO readByName(String dcname) {
		return template.selectOne(MAPPER + ".readByName", dcname);
	}
	
	public DCharDTO readByCId(String dcharId) {
		return template.selectOne(MAPPER + ".readById", dcharId);
	}
	
	public DCharDTO readByNametocid(String dcharId) {
		return template.selectOne(MAPPER + ".readByNametocid", dcharId);
	}
	
	public DCharDTO readById(String dcharId) {
		return template.selectOne(MAPPER + ".readById", dcharId);
	}
	
	public List<DCharDTO> readAllByUid(String uid) {
		return template.selectList(MAPPER + ".readAllByUId", uid);
	}
	
	public List<DCharDTO> readAllAcceptedByGroupId(int groupId) {
		return template.selectList(MAPPER + ".readAllAcceptedByGroupId", groupId);
	}
	
	public List<DCharDTO> readAllAppliedByUid(UidAndGroupId ulg) {
		return template.selectList(MAPPER + ".readAllAppliedByUid", ulg);
	}
	
	public List<DCharDTO> readAllNotAppliedByUid(UidAndGroupId ulg) {
		return template.selectList(MAPPER + ".readAllNotAppliedByUid", ulg);
	}
	
	
	public List<DCharDTO> readAllAppliedByGroupId(int dgroupId) {
		return template.selectList(MAPPER + ".readAllAppliedByGroupId", dgroupId);
	}
	
	public void deleteByName(String dcname) {
		template.delete(MAPPER + ".deleteByName", dcname);
	}
	public void deleteById(String dcharId) {
		template.delete(MAPPER + ".deleteById", dcharId);
	}
}
