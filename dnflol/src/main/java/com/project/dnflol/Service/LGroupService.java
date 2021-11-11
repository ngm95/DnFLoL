package com.project.dnflol.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.dnflol.DAO.LGroupDAO;
import com.project.dnflol.DTO.LGroupDTO;
import com.project.dnflol.Exception.NoSuchGroupException;
import com.project.dnflol.util.BoardMinMax;

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
	
	public int readlgroupId(LGroupDTO lgroupDto) {
		return lgroupDao.readLgroupId(lgroupDto);
	}
	
	public LGroupDTO readById(int lgroupId) {
		return lgroupDao.readById(lgroupId);
	}
	
	public List<LGroupDTO> readAllByOwnerName(String onwerName) {
		return lgroupDao.readAllByOwnerName(onwerName);
	}
	
	public List<LGroupDTO> readAllByDetail(String lgropDetail) {
		return lgroupDao.readAllByDetail(lgropDetail);
	}
	
	public List<LGroupDTO> readAllByGroupName(String lgroupName) {
		return lgroupDao.readAllByGroupName(lgroupName);
	}
	
	public List<LGroupDTO> readAllByUId(String uid) {
		return lgroupDao.readAllByUId(uid);
	}
	
	public List<LGroupDTO> readLimitList(BoardMinMax bmm) {
		return lgroupDao.readLimitList(bmm);
	}
	
	public int readMaxCount() {
		return lgroupDao.readMaxCount();
	}
	
	public void deleteById(int lgroupId) {
		if (lgroupDao.readById(lgroupId) == null)
			throw new NoSuchGroupException("게시글이 존재하지 않습니다.");
		lgroupDao.deleteById(lgroupId);
	}
}
