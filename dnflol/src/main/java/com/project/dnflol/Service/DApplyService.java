package com.project.dnflol.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.dnflol.DAO.DApplyDAO;
import com.project.dnflol.DTO.DApplyDTO;
import com.project.dnflol.Exception.AlreadyExistedApplyException;

@Service
public class DApplyService {
	
	@Autowired
	private DApplyDAO dapplyDao;
	public DApplyService(DApplyDAO dapplyDao) {
		this.dapplyDao = dapplyDao;
	}
	
	public void create(DApplyDTO dapplyDto) {
		if (dapplyDao.read(dapplyDto) == null)
			dapplyDao.create(dapplyDto);
		else
			throw new AlreadyExistedApplyException("이 계정은 이미 해당 그룹에 지원한 상태입니다.");
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
		dapplyDao.updateDapplyResult(dapplyDto);
	}
	
	public void delete(DApplyDTO dapplyDto) {
		dapplyDao.delete(dapplyDto);
	}
}