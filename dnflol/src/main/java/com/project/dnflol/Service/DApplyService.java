package com.project.dnflol.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.dnflol.DAO.DApplyDAO;
import com.project.dnflol.DAO.DGroupDAO;
import com.project.dnflol.DTO.DApplyDTO;
import com.project.dnflol.Exception.AlreadyExistedApplyException;
import com.project.dnflol.Exception.NoSuchApplyException;
import com.project.dnflol.Exception.TooManyApplyException;

@Service
public class DApplyService {
	
	@Autowired
	private DApplyDAO dapplyDao;
	
	public DApplyService(DApplyDAO dapplyDao) {
		this.dapplyDao = dapplyDao;
	}
	
	@Autowired
	private DGroupDAO dgroupDao;
	
	public void create(DApplyDTO dapplyDto) {
		if (dapplyDao.read(dapplyDto) != null)
			throw new AlreadyExistedApplyException(dapplyDto.getDcname() + "은/는 이미 해당 그룹에 지원한 상태입니다.");
		
		dapplyDao.create(dapplyDto);
	}
	
	public void createToAccepted(DApplyDTO dapplyDto) {
		if (dapplyDao.read(dapplyDto) != null)
			throw new AlreadyExistedApplyException(dapplyDto.getDcname() + "은/는 이미 해당 그룹에 지원한 상태입니다.");
		
		dapplyDao.createToAccepted(dapplyDto);
	}
	
	public DApplyDTO read(DApplyDTO dapplyDto) {
		DApplyDTO apply = dapplyDao.read(dapplyDto);
		if (apply == null)
			throw new NoSuchApplyException("해당하는 가입 요청이 없습니다.");
		return apply;
	}
	
	public List<DApplyDTO> readAllAcceptedByGroupId(int dgroupId) {
		return dapplyDao.readAllAcceptedByGroupId(dgroupId);
	}
	
	public List<DApplyDTO> readAllByGroupId(int dgroupId) {
		return dapplyDao.readAllByGroupId(dgroupId);
	}
	
	public List<DApplyDTO> readAllMyApply(String uid) {
		return dapplyDao.readAllMyApply(uid);
	}
	
	public void updateResult(DApplyDTO dapplyDto) {
		if (dapplyDto.getDapplyResult().equals("ACCEPTED") && dapplyDao.readAcceptedCountByGroupId(dapplyDto.getDgroupId()) >= dgroupDao.readGroupMaxByGroupId(dapplyDto.getDgroupId()))
			throw new TooManyApplyException("이미 모든 자리가 다 찼습니다.");
		
		dapplyDao.updateLapplyResult(dapplyDto);
	}
	
	public void delete(DApplyDTO dapplyDto) {
		try {
			dapplyDao.readById(dapplyDto.getDapplyId());
			dapplyDao.delete(dapplyDto);
		} catch (NoSuchApplyException nsae) {
			throw nsae;
		}
			
	}
}