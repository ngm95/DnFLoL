package com.project.dnflol.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.dnflol.DAO.LApplyDAO;
import com.project.dnflol.DAO.LGroupDAO;
import com.project.dnflol.DTO.LApplyDTO;
import com.project.dnflol.DTO.LCharDTO;
import com.project.dnflol.Exception.AlreadyExistedApplyException;
import com.project.dnflol.Exception.TooManyApplyException;

@Service
public class LApplyService {
	
	@Autowired
	private LApplyDAO lapplyDao;
	
	public LApplyService(LApplyDAO lapplyDao) {
		this.lapplyDao = lapplyDao;
	}
	
	@Autowired
	private LGroupDAO lgroupDao;
	
	public void create(LApplyDTO lapplyDto) {
		if (lapplyDao.readAcceptedCountByGroupId(lapplyDto.getLapplyId()) >= lgroupDao.readGroupMaxByGroupId(lapplyDto.getLgroupId()))
			throw new TooManyApplyException("이미 모든 자리가 다 찼습니다.");
		
		if (lapplyDao.read(lapplyDto) != null)
			throw new AlreadyExistedApplyException(lapplyDto.getLcharName() + "은/는 이미 해당 그룹에 지원한 상태입니다.");
		
		lapplyDao.create(lapplyDto);
	}
	
	public void createToAccepted(LApplyDTO lapplyDto) {
		if (lapplyDao.readAcceptedCountByGroupId(lapplyDto.getLapplyId()) >= lgroupDao.readGroupMaxByGroupId(lapplyDto.getLgroupId()))
			throw new TooManyApplyException("이미 모든 자리가 다 찼습니다.");
		
		if (lapplyDao.read(lapplyDto) != null)
			throw new AlreadyExistedApplyException(lapplyDto.getLcharName() + "은/는 이미 해당 그룹에 지원한 상태입니다.");
		
		lapplyDao.createToAccepted(lapplyDto);
	}
	
	public LApplyDTO read(LApplyDTO lapplyDto) {
		return lapplyDao.read(lapplyDto);
	}
	
	public List<LApplyDTO> readAllAcceptedByGroupId(int lgroupId) {
		return lapplyDao.readAllAcceptedByGroupId(lgroupId);
	}
	
	public List<LApplyDTO> readAllByGroupId(int groupId) {
		return lapplyDao.readAllByGroupId(groupId);
	}
	
	public List<LApplyDTO> readAllMyApply(String uid) {
		return lapplyDao.readAllMyApply(uid);
	}
	
	public void updateResult(LApplyDTO lapplyDto) {
		if (lapplyDao.readAcceptedCountByGroupId(lapplyDto.getLapplyId()) >= lgroupDao.readGroupMaxByGroupId(lapplyDto.getLapplyId()))
			throw new TooManyApplyException("이미 모든 자리가 다 찼습니다.");
		
		lapplyDao.updateLapplyResult(lapplyDto);
	}
	
	public void delete(LApplyDTO lapplyDto) {
		lapplyDao.delete(lapplyDto);
	}
}
