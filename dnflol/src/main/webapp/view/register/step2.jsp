<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/view/includes/00_head.jsp" %>
    <%@ include file="/view/includes/03_header.jsp" %>
    <title>회원가입 정보 입력</title>
</head>
<body class="signup-pages">

    <div>
        <div>
            <a href="/"><b>D</b>n<b>F</b> <b>L</b>o<b>L</b></a>
        </div>

        <div class="signup-box-body">
            <p class="box-msg">Register a new membership</p>

       <form:form modelAttribute="request" action="/register/step3" method="post">
                <div class="form-group has-feedback">
                	<label for="uid">아이디</label>
                    <form:input type="text" class="form-control" placeholder="ID" path="uid" id="uid"/>
                    <span class="glyphicon glyphicon-user form-control-feedback"></span>
                    <form:errors path="uid" class="signup-errors"/>
                </div>
                <div class="form-group has-feedback">
               	 	<label for="uname">닉네임</label>
                    <form:input type="text" class="form-control" placeholder="NAME" path="uname" id="uname"/>
                    <span class="glyphicon glyphicon-user form-control-feedback"></span>
                    <form:errors path="uname" class="signup-errors"/>
                </div>
                <div class="form-group has-feedback">
                	<label for="pw">비밀번호</label>
                    <form:input type="password" class="form-control" placeholder="PASSWORD" path="pw" id="pw"/>
                    <span class="glyphicon glyphicon-lock form-control-feedback"></span>
                    <form:errors path="pw" class="signup-errors"/>
                </div>
                <div class="form-group has-feedback">
                	<label for="checkPw">비밀번호 재입력</label>
                    <form:input type="password" class="form-control" placeholder="Retype PASSWORD" path="checkPw" id="checkPw"/>
                    <span class="glyphicon glyphicon-lock form-control-feedback"></span>
                    <form:errors path="checkPw" class="signup-errors"/>
                </div>
                
                <div class="row">
                    <div class="col-xs-8">
                    </div>
                    <div class="col-xs-3">
                        <button type="submit" class="btn btn-style">sign up</button>
                    </div>
                </div>
            </form:form>
            <div class="social-auth-links text-center">
                <p>- OR -</p>
            </div>

            <p>이미 회원가입 하셨나요?</p><a href="/security/login" class="text-center">로그인하기</a>
        </div>
    </div>

</body>
</html>