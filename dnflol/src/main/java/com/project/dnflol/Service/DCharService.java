package com.project.dnflol.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.dnflol.DAO.DCharDAO;
import com.project.dnflol.DTO.DCharDTO;
import com.project.dnflol.Exception.AlreadyExistedLCharNameException;
import com.project.dnflol.Exception.NoSuchCharException;
import com.project.dnflol.util.UidAndGroupId;

@Service
public class DCharService {
	
	private DCharDAO dcharDao;
	
	@Autowired
	public DCharService(DCharDAO dcharDao) {
		this.dcharDao = dcharDao;
	}
	
	public void create(DCharDTO dcharDto) {
		try {
			readByName(dcharDto.getDcname());		// 해당 이름으로 캐릭터를 찾아 봄
			throw new AlreadyExistedLCharNameException("<b>" + dcharDto.getDcserver() + "</b>서버의 <b>" + dcharDto.getDcname() + "</b>은/는 이미 다른 계정과 연동되어 있습니다.");
		} catch (NoSuchCharException nsce) {		// 해당 이름의 캐릭터를 찾을 수 없어야 새로운 캐릭터 생성 가능
			dcharDao.create(dcharDto);
		}
	}
	
	public DCharDTO readByName(String dcname) {
		DCharDTO dcharDto = dcharDao.readByName(dcname);
		if (dcharDto == null)
			throw new NoSuchCharException("해당하는 DnF 캐릭터가 없습니다.");
		return dcharDto; 
	}
	public DCharDTO readByCId(String dcharId) {
		DCharDTO dcharDto = dcharDao.readByCId(dcharId);
		if (dcharDto == null)
			throw new NoSuchCharException("해당하는 DnF 캐릭터가 없습니다.");
		return dcharDto; 
	}
	
	public DCharDTO readByNametocid(String dcharId) {
		DCharDTO dcharDto = dcharDao.readByNametocid(dcharId);
		if (dcharDto == null)
			throw new NoSuchCharException("해당하는 DnF 캐릭터가 없습니다.");
		return dcharDto; 
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
		DCharDTO dcharDto = readByCId(dcharId);
		if (dcharDto != null)
			dcharDao.deleteById(dcharId);
	}
}
