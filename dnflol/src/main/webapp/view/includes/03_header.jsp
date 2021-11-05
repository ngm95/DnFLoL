<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<nav class="navbar navbar-expand-lg navbar-light" style="background-color:#e3f2fd; margin-bottom:10px">
	<div class="container-fluid">
		<a class="navbar-brand" href="/"><b>D</b>n<b>F</b><b>L</b>o<b>L</b></a>
		<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarSupportedContent">
			<ul class="navbar-nav me-auto mb-2 mb-lg-0">
				<li class="nav-item"><a class="nav-link active" aria-current="page" href="/">홈 화면</a></li>
				<li class="nav-item"><a class="nav-link" href="/dnf/board">DnF 게시판</a></li>
				<li class="nav-item"><a class="nav-link" href="/lol/board">LoL 게시판</a></li>
			</ul>
			<ul class="navbar-nav d-flex">
				<sec:authorize access="isAnonymous()">
					<li class="nav-item"><a class="nav-link"href="/security/login">로그인</a></li>
					<li class="nav-item"><a class="nav-link" href="/register/step1">회원가입</a></li>
				</sec:authorize>
				<sec:authorize access="isAuthenticated()">
					<li class="nav-item dropdown"><a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false" style="font-size:1.2rem">Dnf 신청 관리</a>
						<ul id="dnfNotice" class="dropdown-menu" aria-labelledby="navbarDropdown">
							
						</ul>
					</li>
					<li class="nav-item dropdown"><a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false" style="font-size:1.2rem">LoL 신청 관리</a>
						<ul id="lolNotice" class="dropdown-menu" aria-labelledby="navbarDropdown">
							
						</ul>
					</li>
					<li class="nav-item"><a class="nav-link" href="/user/myPage">내 정보</a></li>
					<li class="nav-item"><a class="nav-link" href="/security/logout">로그아웃</a></li>
				</sec:authorize>
			</ul>
			<input type="hidden" value="${authInfo.uname}">
		</div>
	</div>
</nav>
