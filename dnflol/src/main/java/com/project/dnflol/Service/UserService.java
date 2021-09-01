package com.project.dnflol.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.dnflol.DAO.UserDAO;
import com.project.dnflol.DTO.UserDTO;
import com.project.dnflol.Exception.AlreadyExistedUIdException;
import com.project.dnflol.Exception.NoSuchUIdException;

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
}
