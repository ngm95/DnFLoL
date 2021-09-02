package com.project.dnflol.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.dnflol.DAO.LGroupDAO;
import com.project.dnflol.DTO.LGroupDTO;

@Service
public class LGroupService {
	
	private LGroupDAO lgroupDao;
	@Autowired
	public LGroupService(LGroupDAO lgroupDao) {
		this.lgroupDao = lgroupDao;
	}
	
	public void create(LGroupDTO lgroupDto) {
		lgroupDao.create(lgroupDto);
	}
	
	public LGroupDTO readById(int lgroupId) {
		return lgroupDao.readById(lgroupId);
	}
	
	public List<LGroupDTO> readAllByOwner(int lgroupOwner) {
		return lgroupDao.readAllByOwner(lgroupOwner);
	}
	
	public void deleteById(int groupId) {
		lgroupDao.deleteById(groupId);
	}
}
