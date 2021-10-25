<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/view/includes/00_head.jsp" %>
<title>로그인 페이지</title>
</head>
<body class="signup-pages">

	<div class="container" style="height: 100%">
		<%@ include file="/view/includes/03_header.jsp"%>
		<div class="signup-logo">
			<a href="/"><b>D</b>n<b>F</b> <b>L</b>o<b>L</b></a>
		</div>

		<div class="signup-box-body">
			<p class="box-msg">로그인 양식</p>

			<form class="px-4 py-3" action='<c:url value="/security/login"/>' method="post">
				<div class="form-group">
					<label for="exampleDropdownFormEmail1">아이디</label> 
					<input type="text" class="form-control" name="username" placeholder="ID">
				</div>
				<div class="form-group">
					<label for="exampleDropdownFormPassword1">비밀번호</label> 
					<input type="password" class="form-control" name="password" placeholder="Password">
				</div>
				<c:if test="${not empty ERRORMSG}">
					<font color="red">
						<p>
							${ERRORMSG}
						</p>
					</font>
				</c:if>
				<input name="${_csrf.parameterName}" type="hidden" value="${_csrf.token}" />
				<div class="dropdown-divider pull-right"></div>
			<a class="dropdown-item pull-right" href="/register/step1">회원가입하기</a>
				<button type="submit" class="btn btn-primary">로그인</button>
			</form>
			
		</div>

	</div>
	<%@ include file="/view/includes/09_footer.jsp"%>
</body>
</html>