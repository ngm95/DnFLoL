package com.project.dnflol.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.dnflol.DAO.LApplyDAO;
import com.project.dnflol.DTO.LApplyDTO;
import com.project.dnflol.Exception.AlreadyExistedApplyException;

@Service
public class LApplyService {
	
	@Autowired
	private LApplyDAO lapplyDao;
	
	public LApplyService(LApplyDAO lapplyDao) {
		this.lapplyDao = lapplyDao;
	}
	
	public void create(LApplyDTO lapplyDto) {
		if (lapplyDao.read(lapplyDto) == null)
			lapplyDao.create(lapplyDto);
		else
			throw new AlreadyExistedApplyException("이 계정은 이미 해당 그룹에 지원한 상태입니다.");
	}
	
	public LApplyDTO read(LApplyDTO lapplyDto) {
		return lapplyDao.read(lapplyDto);
	}
	
	public List<LApplyDTO> readAllByGroupId(int groupId) {
		return lapplyDao.readAllByGroupId(groupId);
	}
	
	public void updateResult(LApplyDTO lapplyDto) {
		lapplyDao.updateLapplyResult(lapplyDto);
	}
	
	public void delete(LApplyDTO lapplyDto) {
		lapplyDao.delete(lapplyDto);
	}
}
