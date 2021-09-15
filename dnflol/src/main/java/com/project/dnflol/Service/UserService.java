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
	
	private UserDAO userDao;
	
	@Autowired
	public UserService(UserDAO userDao) {
		this.userDao = userDao;
	}
	
	public void create(UserDTO userDto) {
		if (userDao.readById(userDto.getUid()) == null)
			userDao.create(userDto);
		else
			throw new AlreadyExistedUIdException(userDto.getUid());
	}
	
	public void updatePw(UserDTO userDto) {
		if (userDao.readById(userDto.getUid()) == null)
			throw new NoSuchUIdException(userDto.getUid());
		else
			userDao.updatePw(userDto);
	}
	
	public void updateName(UserDTO userDto) {
		if (userDao.readById(userDto.getUid()) == null)
			throw new NoSuchUIdException(userDto.getUid());
		else
			userDao.updateName(userDto);
	}
	
	public UserDTO readByName(String uname) {
		return userDao.readByName(uname);
	}
	
	public UserDTO readById(String uid) {
		return userDao.readById(uid);
	}
	
	public UserDTO readByEmail(String email) {
		return userDao.readByEmail(email);
	}
	
	public void register(RegisterRequest regReq) {
		if (readByEmail(regReq.getEmail()) != null) 
			throw new AlreadyExistedEmailException(regReq.getEmail());
		else if (readById(regReq.getUid()) != null)
			throw new AlreadyExistedUIdException(regReq.getUid());
		else if (readByName(regReq.getUname()) != null) 
			throw new AlreadyExistedUNameException(regReq.getUid());
		else
			userDao.register(regReq);
	}
	
	public void delete(String uid) {
		userDao.delete(uid);
	}
}
