package com.project.dnflol.util;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Entity
@Data
public class RegisterRequest {
	@Column
	@Pattern(regexp="\\w{3,8}", message="아이디를 3~8자로 입력해주세요.")
	private String uid;
	
	@Column
	@Pattern(regexp="\\S{2,8}", message="닉네임을 2~8자로 입력해주세요.")
	private String uname;
	
	@Column
	@NotEmpty(message="이메일을 입력해주세요.")
	@Email(message="올바른 형식이 아닙니다.")
	private String email;
	
	@Column
	@Size(min=4, max=16, message="비밀번호를 4~16자로 입력해주세요.")
	private String pw;
	
	@Column
	@Size(min=4, max=16, message="비밀번호를 4~16자로 입력해주세요.")
	private String checkPw;
	
	public boolean isPwEqualtoCheckPw() {
		return pw.equals(checkPw);
	}
}
