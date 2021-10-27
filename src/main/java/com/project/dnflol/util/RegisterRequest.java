package com.project.dnflol.util;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class RegisterRequest {
	@Column
	@NotBlank(message="반드시 입력해야 합니다")
	@Pattern(regexp="\\w{3,8}", message="아이디를 3~8자로 입력해주세요.")
	private String uid;
	
	@Column
	@NotBlank(message="반드시 입력해야 합니다")
	@Pattern(regexp="\\S{2,8}", message="닉네임을 2~8자로 입력해주세요.")
	private String uname;
	
	@Column
	@NotBlank(message="반드시 입력해야 합니다")
	@Size(min=4, max=16, message="비밀번호를 4~16자로 입력해주세요.")
	private String pw;
	
	@Column
	@NotBlank(message="반드시 입력해야 합니다")
	@Size(min=4, max=16, message="비밀번호를 4~16자로 입력해주세요.")
	private String checkPw;
	
	public boolean isPwEqualtoCheckPw() {
		return pw.equals(checkPw);
	}
	
	public void setPw(String pw) {
		this.pw = pw;
	}
}
