<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@ page import="com.project.dnflol.Security.CustomUserDetails" %>
<%@ page import="org.springframework.security.core.Authentication"%>
<%

%>
<div class="header">
	<sec:authorize access="isAnonymous()">
		<ul class="nav nav-pills pull-right">
			<li role="presentation"><a href="/security/login">로그인</a></li>
			<li role="presentation"><a href="/register/step1">회원가입</a></li>
		</ul>
	</sec:authorize>
	<sec:authorize access="isAuthenticated()">
		<ul class="nav nav-pills pull-right">
			<li role="presentation"><a href="#">${authInfo.uname}님 반갑습니다</a></li>
			<li role="presentation"><a href="/user/myPage">내 정보</a></li>
			<li role="presentation"><a href="/security/logout">로그아웃</a></li>
		</ul>
	</sec:authorize>

	<div>
		<ul class="nav nav-pills">
			<li role="presentation"><h3 class="text-muted"><a href="/"><b>D</b>n<b>F</b> <b>L</b>o<b>L</b></a></h3></li>
			<li role="presentation"><a href="/dnf/board">DnF 페이지</a></li>
			<li role="presentation"><a href="/lol/board">LoL 페이지</a></li>
		</ul>
	</div>
</div>