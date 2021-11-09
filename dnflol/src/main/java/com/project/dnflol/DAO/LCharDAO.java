package com.project.dnflol.DAO;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.project.dnflol.DTO.LCharDTO;
import com.project.dnflol.util.UidAndGroupId;

@Repository
public class LCharDAO {
	private static final String MAPPER = "LCharMapper";
	
	@Autowired
	SqlSessionTemplate template;
	
	public void create(LCharDTO lcharDto) {
		template.insert(MAPPER + ".create", lcharDto);
	}
	
	public LCharDTO readByName(String lcharName) {
		return template.selectOne(MAPPER + ".readByName", lcharName);
	}
	
	public List<LCharDTO> readAllByUid(String uid) {
		return template.selectList(MAPPER + ".readAllByUId", uid);
	}
	
	public List<LCharDTO> readAllAcceptedByGroupId(int lgroupId) {
		return template.selectList(MAPPER + ".readAllAcceptedByGroupId", lgroupId);
	}
	
	public List<LCharDTO> readAllAppliedByUid(UidAndGroupId ulg) {
		return template.selectList(MAPPER + ".readAllAppliedByUid", ulg);
	}
	
	public List<LCharDTO> readAllNotAppliedByUid(UidAndGroupId ulg) {
		return template.selectList(MAPPER + ".readAllNotAppliedByUid", ulg);
	}
	
	public List<LCharDTO> readAllAppliedByGroupId(int lgroupId) {
		return template.selectList(MAPPER + ".readAllAppliedByGroupId", lgroupId);
	}
	
	public void deleteByName(String lcharName) {
		template.delete(MAPPER + ".deleteByName", lcharName);
	}
}
