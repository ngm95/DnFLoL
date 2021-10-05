package com.project.dnflol.Security;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserAuthDAO {
	private static final String MAPPER = "UserMapper";
	
	@Autowired
	private SqlSessionTemplate template;

	public CustomUserDetails getUserById(String uid) {
		return template.selectOne(MAPPER + ".readUserById", uid);
	}
}
