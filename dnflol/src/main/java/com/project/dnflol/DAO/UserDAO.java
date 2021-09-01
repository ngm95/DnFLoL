package com.project.dnflol.DAO;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.project.dnflol.DTO.UserDTO;

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
	
	public void updatePw(UserDTO userDto) {
		sqlsession.update(MAPPER + ".updatePw", userDto);
	}
	
	public void updateName(UserDTO userDto) {
		sqlsession.update(MAPPER + ".updateName", userDto);
	}
	
	
}
