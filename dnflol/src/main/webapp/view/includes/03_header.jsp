<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@ page import="com.project.dnflol.Security.CustomUserDetails" %>
<%@ page import="org.springframework.security.core.Authentication"%>

<script src="/bootstrap/js/bootstrap.bundle.min.js"></script>

<nav class="navbar navbar-expand-lg navbar-light" style="background-color:#e3f2fd; margin-bottom:10px">
	<div class="container-fluid">
		<a class="navbar-brand" href="#"><b>D</b>n<b>F</b><b>L</b>o<b>L</b></a>
		<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarSupportedContent">
			<ul class="navbar-nav me-auto mb-2 mb-lg-0">
				<li class="nav-item"><a class="nav-link active" aria-current="page" href="/">Home</a></li>
				<li class="nav-item"><a class="nav-link" href="/dnf/board">DnF</a></li>
				<li class="nav-item"><a class="nav-link" href="/lol/board">LoL</a></li>
			</ul>
			<ul class="navbar-nav d-flex">
				<sec:authorize access="isAnonymous()">
					<li class="nav-item"><a class="nav-link"href="/security/login">로그인</a></li>
					<li class="nav-item"><a class="nav-link" href="/register/step1">회원가입</a></li>
				</sec:authorize>
				<sec:authorize access="isAuthenticated()">
					<li class="nav-item"><a class="nav-link" href="#"><b>${authInfo.uname}</b></a></li>
					<li class="nav-item"><a class="nav-link" href="/user/myPage">내 정보</a></li>
					<li class="nav-item"><a class="nav-link" href="/security/logout">로그아웃</a></li>
				</sec:authorize>
			</ul>
			
		</div>
	</div>
</nav>