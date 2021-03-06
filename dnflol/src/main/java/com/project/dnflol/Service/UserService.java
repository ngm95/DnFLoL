package com.project.dnflol.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.dnflol.DAO.UserDAO;
import com.project.dnflol.DTO.UserDTO;
import com.project.dnflol.Exception.AlreadyExistedEmailException;
import com.project.dnflol.Exception.AlreadyExistedUIdException;
import com.project.dnflol.Exception.AlreadyExistedUNameException;
import com.project.dnflol.Exception.NoSuchUIdException;
import com.project.dnflol.util.RegisterRequest;

@Service
public class UserService {
	
	@Autowired
	private UserDAO uMapper;
	
	public void create(UserDTO userDto) {
		if (uMapper.readById(userDto.getUid()) == null)
			uMapper.create(userDto);
		else
			throw new AlreadyExistedUIdException(userDto.getUid() + "는 이미 존재하는 아이디입니다.");
	}
	
	public void updatePw(UserDTO userDto) {
		if (uMapper.readById(userDto.getUid()) == null)
			throw new NoSuchUIdException(userDto.getUid() + "라는 아이디는 존재하지 않습니다.");
		else
			uMapper.updatePw(userDto);
	}
	
	public void updateName(UserDTO userDto) {
		if (uMapper.readById(userDto.getUid()) == null)
			throw new NoSuchUIdException(userDto.getUid() + "라는 아이디는 존재하지 않습니다.");
		else
			uMapper.updateName(userDto);
	}
	
	public UserDTO readByName(String uname) {
		return uMapper.readByName(uname);
	}
	
	public UserDTO readById(String uid) {
		return uMapper.readById(uid);
	}
	
	public void register(RegisterRequest regReq) {
		if (readById(regReq.getUid()) != null)
			throw new AlreadyExistedUIdException("해당 아이디를 사용하는 계정이 이미 존재합니다.");
		else if (readByName(regReq.getUname()) != null) 
			throw new AlreadyExistedUNameException("해당 닉네임을 사용하는 계정이 이미 존재합니다.");
		else
			uMapper.register(regReq);
	}
	
	public void delete(String uid) {
		uMapper.delete(uid);
	}
	
	
}
