package com.project.dnflol.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.dnflol.DAO.LCharDAO;
import com.project.dnflol.DTO.LCharDTO;
import com.project.dnflol.Exception.AlreadyExistedLCharNameException;

@Service
public class LCharService {
	
	private LCharDAO lcharDao;
	
	@Autowired
	public LCharService(LCharDAO lcharDao) {
		this.lcharDao = lcharDao;
	}
	
	public void create(LCharDTO lcharDto) {
		if (readByName(lcharDto.getLcharName()) == null)
			lcharDao.create(lcharDto);
		else
			throw new AlreadyExistedLCharNameException(lcharDto.getLcharName());
	}
	
	public LCharDTO readByName(String lcharName) {
		return lcharDao.readByName(lcharName);
	}
	
	public List<LCharDTO> readAllByUid(String uid) {
		return lcharDao.readAllByUid(uid);
	}
	
	public void deleteById(String lcharId) {
		lcharDao.deleteById(lcharId);
	}
	
	public void deleteByName(String lcharName) {
		lcharDao.deleteByName(lcharName);
	}
}
