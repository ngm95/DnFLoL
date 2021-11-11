package com.project.dnflol.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.dnflol.DAO.DCharDAO;
import com.project.dnflol.DTO.DCharDTO;
import com.project.dnflol.Exception.AlreadyExistedLCharNameException;
import com.project.dnflol.util.UidAndGroupId;

@Service
public class DCharService {
	
	private DCharDAO dcharDao;
	
	@Autowired
	public DCharService(DCharDAO dcharDao) {
		this.dcharDao = dcharDao;
	}
	
	public void create(DCharDTO dcharDto) {
		if (readByName(dcharDto.getDcname()) == null)
			dcharDao.create(dcharDto);
		else
			throw new AlreadyExistedLCharNameException(dcharDto.getDcname() + "는 이미 다른 계정과 연동되어 있습니다.");
	}
	
	public DCharDTO readById(String dcharId) {
		return dcharDao.readById(dcharId);
	}
	
	public DCharDTO readByName(String dcharName) {
		return dcharDao.readByName(dcharName);
	}
	
	public DCharDTO readByNametocid(String dcharId) {
		return dcharDao.readByNametocid(dcharId);
	}
	
	public List<DCharDTO> readAllByUid(String uid) {
		return dcharDao.readAllByUid(uid);
	}
	
	public List<DCharDTO> readAllAcceptedByGroupId(int groupId) {
		return dcharDao.readAllAcceptedByGroupId(groupId);
	}
	
	public List<DCharDTO> readAllAppliedByUid(String uid, int dgroupId) {
		return dcharDao.readAllAppliedByUid(new UidAndGroupId(uid, dgroupId));
	}
	
	public List<DCharDTO> readAllNotAppliedByUid(String uid, int dgroupId) {
		return dcharDao.readAllNotAppliedByUid(new UidAndGroupId(uid, dgroupId));
	}

	
	public List<DCharDTO> readAllAppliedByGroupId(int dgroupId) {
		return dcharDao.readAllAppliedByGroupId(dgroupId);
	}
	
	public void deleteById(String dcharId) {
		dcharDao.deleteById(dcharId);
	}
	
	public void deleteByName(String dcharName) {
		dcharDao.deleteByName(dcharName);
	}
}
