<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/view/includes/00_head.jsp" %>
    <title>회원가입 정보 입력</title>
</head>
<body class="signup-pages">
	<div class="jumbotron contents-wrap" style="height: 100%">
		<%@ include file="/view/includes/03_header.jsp"%>

		<h3>회원가입 정보 입력</h3>
		<form:form modelAttribute="request" action="/register/step3" method="post">
			<div class="form-group has-feedback" style="margin-top:10px; margin-bottom:10px">
				<label for="uid">아이디</label>
				<form:input type="text" class="form-control" placeholder="아이디" path="uid" id="uid" />
				<span class="glyphicon glyphicon-user form-control-feedback"></span>
				<form:errors path="uid" class="signup-errors" />
			</div>
			<div class="form-group has-feedback" style="margin-bottom:10px;">
				<label for="uname">닉네임</label>
				<form:input type="text" class="form-control" placeholder="닉네임" path="uname" id="uname" />
				<span class="glyphicon glyphicon-user form-control-feedback"></span>
				<form:errors path="uname" class="signup-errors" />
			</div>
			<div class="form-group has-feedback" style="margin-bottom:10px;">
				<label for="pw">비밀번호</label>
				<form:input type="password" class="form-control" placeholder="비밀번호" path="pw" id="pw" />
				<span class="glyphicon glyphicon-lock form-control-feedback"></span>
				<form:errors path="pw" class="signup-errors" />
			</div>
			<div class="form-group has-feedback" style="margin-bottom:10px;">
				<label for="checkPw">비밀번호 재입력</label>
				<form:input type="password" class="form-control" placeholder="비밀번호 재입력" path="checkPw" id="checkPw" />
				<span class="glyphicon glyphicon-lock form-control-feedback"></span>
				<form:errors path="checkPw" class="signup-errors" />
			</div>

			<button type="submit" class="btn btn-primary" style="float: right">회원가입</button>
		</form:form>
		<%@ include file="/view/includes/09_footer.jsp"%>
	</div>
</body>
</html>