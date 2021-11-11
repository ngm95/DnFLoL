package com.project.dnflol.DAO;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.project.dnflol.DTO.LGroupDTO;
import com.project.dnflol.util.BoardMinMax;

@Repository
public class LGroupDAO {
	private static final String MAPPER = "LGroupMapper";
	
	@Autowired
	SqlSessionTemplate template;
	
	public void create(LGroupDTO lgroupDto) {
		template.insert(MAPPER + ".create", lgroupDto);
	}
	
	public int readLgroupId(LGroupDTO lgroupDto) {
		return template.selectOne(MAPPER + ".readlgroupId", lgroupDto);
	}
	
	public int readGroupMaxByGroupId(int groupId) {
		return template.selectOne(MAPPER + ".readGroupMaxByGroupId", groupId);
	}
	
	public LGroupDTO readById(int lgroupId) {
		return template.selectOne(MAPPER + ".readById", lgroupId); 
	}
	
	public List<LGroupDTO> readAllByOwnerName(String onwerName) {
		return template.selectList(MAPPER + ".readAllByOwnerName", onwerName);
	}
	
	public List<LGroupDTO> readAllByDetail(String lgropDetail) {
		return template.selectList(MAPPER + ".readAllByDetail", lgropDetail);
	}
	
	public List<LGroupDTO> readAllByGroupName(String lgroupName) {
		return template.selectList(MAPPER + ".readAllByGroupName", lgroupName);
	}
	
	public List<LGroupDTO> readAllByUId(String uid) {
		return template.selectList(MAPPER + ".readAllByUId", uid);
	}
	
	public List<LGroupDTO> readLimitList(BoardMinMax bmm) {
		return template.selectList(MAPPER + ".readLimitList", bmm);
	}
	
	public int readMaxCount() {
		return template.selectOne(MAPPER + ".readMaxCount");
	}
	
	public void deleteById(int lgroupId) {
		template.delete(MAPPER + ".deleteById", lgroupId);
	}
}
