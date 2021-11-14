package com.project.dnflol.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.dnflol.DAO.LCharDAO;
import com.project.dnflol.DTO.LCharDTO;
import com.project.dnflol.Exception.AlreadyExistedLCharNameException;
import com.project.dnflol.Exception.NoSuchCharException;
import com.project.dnflol.util.UidAndGroupId;

@Service
public class LCharService {
	
	private LCharDAO lcharDao;
	
	@Autowired
	public LCharService(LCharDAO lcharDao) {
		this.lcharDao = lcharDao;
	}
	
	public void create(LCharDTO lcharDto) {
		try {
			readByName(lcharDto.getLcharName());		// 해당 이름으로 계정을 찾아 봄
			throw new AlreadyExistedLCharNameException("<b>" + lcharDto.getLcharName() + "</b>은/는 이미 다른 계정과 연동되어 있습니다.");
		} catch (NoSuchCharException nsce) {			// 해당 이름의 계정을 찾을 수 없어야 새로운 캐릭터 생성 가능
			lcharDao.create(lcharDto);
		}
	}
	
	public LCharDTO readByName(String lcharName) {
		LCharDTO lcharDto = lcharDao.readByName(lcharName);
		if (lcharDto == null)
			throw new NoSuchCharException("해당하는 LoL 계정이 없습니다.");
		return lcharDto;
	}
	
	public List<LCharDTO> readAllByUid(String uid) {
		return lcharDao.readAllByUid(uid);
	}

	public List<LCharDTO> readAllAppliedByUid(String uid, int lgroupId) {
		return lcharDao.readAllAppliedByUid(new UidAndGroupId(uid, lgroupId));
	}
	
	public List<LCharDTO> readAllNotAppliedByUid(String uid, int lgroupId) {
		return lcharDao.readAllNotAppliedByUid(new UidAndGroupId(uid, lgroupId));
	}

	
	public List<LCharDTO> readAllAppliedByGroupId(int lgroupId) {
		return lcharDao.readAllAppliedByGroupId(lgroupId);
	}
	
	public void deleteByName(String lcharName) {
		LCharDTO lcharDto = readByName(lcharName);
		if (lcharDto != null)
			lcharDao.deleteByName(lcharName);
	}
}
