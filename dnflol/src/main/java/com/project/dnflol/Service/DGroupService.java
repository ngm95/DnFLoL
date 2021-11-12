package com.project.dnflol.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.dnflol.DAO.DGroupDAO;
import com.project.dnflol.DTO.DGroupDTO;
import com.project.dnflol.Exception.NoSuchGroupException;
import com.project.dnflol.util.BoardMinMax;

@Service
public class DGroupService {
	
	private DGroupDAO dgroupDao;
	
	@Autowired
	public DGroupService(DGroupDAO dgroupDao) {
		this.dgroupDao = dgroupDao;
	}
	
	public void create(DGroupDTO dgroupDto) {
		dgroupDao.create(dgroupDto);
	}

	public Integer readDgroupId(DGroupDTO dgroupDto) {
		return dgroupDao.readDgroupId(dgroupDto);
	}
	
	public DGroupDTO readById(int dgroupId) {
		DGroupDTO group = dgroupDao.readById(dgroupId);
		if (group == null)
			throw new NoSuchGroupException("해당하는 게시물이 없습니다.");
		
		return group;
	}
	
	public List<DGroupDTO> readAllByOwnerName(String onwerName) {
		return dgroupDao.readAllByOwnerName(onwerName);
	}
	
	public List<DGroupDTO> readAllByDetail(String dgropDetail) {
		return dgroupDao.readAllByDetail(dgropDetail);
	}
	
	public List<DGroupDTO> readAllByGroupName(String dgroupName) {
		return dgroupDao.readAllByGroupName(dgroupName);
	}
	
	public List<DGroupDTO> readAllByUId(String uid) {
		return dgroupDao.readAllByUId(uid);
	}
	
	public List<DGroupDTO> readLimitList(BoardMinMax bmm) {
		return dgroupDao.readLimitList(bmm);
	}
	
	public int readMaxCount() {
		return dgroupDao.readMaxCount();
	}
	
	public void deleteById(int dgroupId) {
		if (dgroupDao.readById(dgroupId) == null)
			throw new NoSuchGroupException("게시글이 존재하지 않습니다.");
		dgroupDao.deleteById(dgroupId);
	}
}
