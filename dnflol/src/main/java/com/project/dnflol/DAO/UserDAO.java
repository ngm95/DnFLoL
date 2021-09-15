package com.project.dnflol.DAO;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.project.dnflol.DTO.UserDTO;
import com.project.dnflol.Exception.AlreadyExistedEmailException;
import com.project.dnflol.Exception.AlreadyExistedUNameException;
import com.project.dnflol.util.RegisterRequest;

@Repository
public class UserDAO {
	private static final String MAPPER = "UserMapper";
	
	@Resource
	SqlSession sqlsession;
	
	public void create(UserDTO userDto) {
		sqlsession.insert(MAPPER + ".create", userDto);
	}
	
	public UserDTO readById(String uid) {
		return sqlsession.selectOne(MAPPER + ".readById", uid);
	}
	
	public UserDTO readByName(String uname) {
		return sqlsession.selectOne(MAPPER + ".readByName", uname);
	}
	
	public UserDTO readByEmail(String email) {
		return sqlsession.selectOne(MAPPER + ".readByEmail", email);
	}
	
	public void updatePw(UserDTO userDto) {
		sqlsession.update(MAPPER + ".updatePw", userDto);
	}
	
	public void updateName(UserDTO userDto) {
		sqlsession.update(MAPPER + ".updateName", userDto);
	}
	
	public void register(RegisterRequest regReq) {
		sqlsession.insert(MAPPER + ".register", regReq);
	}
	
	public void delete(String uid) {
		sqlsession.delete(MAPPER + ".delete", uid);
	}
}
