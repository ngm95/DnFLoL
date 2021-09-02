package com.project.dnflol.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.dnflol.DAO.LApplyDAO;
import com.project.dnflol.DTO.LApplyDTO;

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
