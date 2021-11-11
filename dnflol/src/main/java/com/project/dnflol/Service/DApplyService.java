package com.project.dnflol.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.dnflol.DAO.DApplyDAO;
import com.project.dnflol.DAO.DGroupDAO;
import com.project.dnflol.DTO.DApplyDTO;
import com.project.dnflol.Exception.AlreadyExistedApplyException;
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
		if (dapplyDao.readAcceptedCountByGroupId(dapplyDto.getDapplyId()) >= dgroupDao.readGroupMaxByGroupId(dapplyDto.getDgroupId()))
			throw new TooManyApplyException("이미 모든 자리가 다 찼습니다.");
		
		if (dapplyDao.read(dapplyDto) != null)
			throw new AlreadyExistedApplyException(dapplyDto.getDcname() + "은/는 이미 해당 그룹에 지원한 상태입니다.");
		
		dapplyDao.create(dapplyDto);
	}
	
	public void createToAccepted(DApplyDTO dapplyDto) {
		if (dapplyDao.readAcceptedCountByGroupId(dapplyDto.getDapplyId()) >= dgroupDao.readGroupMaxByGroupId(dapplyDto.getDgroupId()))
			throw new TooManyApplyException("이미 모든 자리가 다 찼습니다.");
		
		if (dapplyDao.read(dapplyDto) != null)
			throw new AlreadyExistedApplyException(dapplyDto.getDcname() + "은/는 이미 해당 그룹에 지원한 상태입니다.");
		
		dapplyDao.createToAccepted(dapplyDto);
	}
	
	public DApplyDTO read(DApplyDTO dapplyDto) {
		return dapplyDao.read(dapplyDto);
	}
	
	public List<DApplyDTO> readAllByGroupId(int groupId) {
		return dapplyDao.readAllByGroupId(groupId);
	}
	
	public List<DApplyDTO> readAllMyApply(String uid) {
		return dapplyDao.readAllMyApply(uid);
	}
	
	public void updateResult(DApplyDTO dapplyDto) {
		if (dapplyDao.readAcceptedCountByGroupId(dapplyDto.getDapplyId()) >= dgroupDao.readGroupMaxByGroupId(dapplyDto.getDgroupId()))
			throw new TooManyApplyException("이미 모든 자리가 다 찼습니다.");
		
		dapplyDao.updateLapplyResult(dapplyDto);
	}
	
	public void delete(DApplyDTO dapplyDto) {
		dapplyDao.delete(dapplyDto);
	}
}