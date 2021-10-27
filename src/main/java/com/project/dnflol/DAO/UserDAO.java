package com.project.dnflol.DAO;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.project.dnflol.DTO.UserDTO;
import com.project.dnflol.util.RegisterRequest;

@Repository
public class UserDAO {
	private static final String MAPPER = "UserMapper";
	
	@Autowired
	SqlSessionTemplate template;
	
	public void create(UserDTO userDto) {
		template.insert(MAPPER + ".create", userDto);
	}
	
	public void register(RegisterRequest regReq) {
		template.insert(MAPPER + ".create", regReq);
	}
	
	public UserDTO readById(String uid) {
		return template.selectOne(MAPPER + ".readById", uid);
	}
	
	public UserDTO readByName(String uname) {
		return template.selectOne(MAPPER + ".readByName", uname);
	}
	
	public void updatePw(UserDTO userDto) {
		template.update(MAPPER + ".updatePw", userDto);
	}
	
	public void updateName(UserDTO userDto) {
		template.update(MAPPER + ".updateName", userDto);
	}
	
	public void delete(String uid) {
		template.delete(MAPPER + ".delete", uid);
	}
}
