package com.project.dnflol.Security;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository
public class UserAuthDAO {
	private static final String MAPPER = "UserMapper";
	
	@Resource
	private SqlSession sqlSession;

	public CustomUserDetails getUserById(String uid) {
		return sqlSession.selectOne(MAPPER + ".selectUserById", uid);
	}
}
