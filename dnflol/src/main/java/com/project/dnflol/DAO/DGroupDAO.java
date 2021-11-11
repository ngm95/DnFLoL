package com.project.dnflol.DAO;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.project.dnflol.DTO.DGroupDTO;
import com.project.dnflol.util.BoardMinMax;

@Repository
public class DGroupDAO {
	private static final String MAPPER = "DGroupMapper";
	
	@Autowired
	SqlSessionTemplate template;
	
	public void create(DGroupDTO dgroupDto) {
		template.insert(MAPPER + ".create", dgroupDto);
	}
	
	public Integer readDgroupId(DGroupDTO dgroupDto) {
		return template.selectOne(MAPPER + ".readDgroupId", dgroupDto);
	}
	
	public int readGroupMaxByGroupId(int groupId) {
		return template.selectOne(MAPPER + ".readGroupMaxByGroupId", groupId);
	}
	
	public DGroupDTO readById(int dgroupId) {
		return template.selectOne(MAPPER + ".readById", dgroupId); 
	}
	
	public List<DGroupDTO> readAllByOwnerName(String onwerName) {
		return template.selectList(MAPPER + ".readAllByOwner", onwerName);
	}
	
	public List<DGroupDTO> readAllByDetail(String lgropDetail) {
		return template.selectList(MAPPER + ".readAllByDetail", lgropDetail);
	}
	
	public List<DGroupDTO> readAllByGroupName(String dgroupName) {
		return template.selectList(MAPPER + ".readAllByGroupName", dgroupName);
	}
	
	public List<DGroupDTO> readAllByUId(String uid) {
		return template.selectList(MAPPER + ".readAllByUId", uid);
	}
	
	public List<DGroupDTO> readLimitList(BoardMinMax bmm) {
		return template.selectList(MAPPER + ".readLimitList", bmm);
	}
	
	public int readMaxCount() {
		return template.selectOne(MAPPER + ".readMaxCount");
	}
	
	public void deleteById(int dgroupId) {
		template.delete(MAPPER + ".deleteById", dgroupId);
	}
}
