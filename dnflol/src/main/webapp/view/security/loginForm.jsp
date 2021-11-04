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
<body>

	<div class="container contents-wrap" style="height: 100%">
		<%@ include file="/view/includes/03_header.jsp"%>

		<div class="jumbotron">
			<h3>로그인 양식</h3>

			<form class="px-4 py-3" action="/security/login" method="post">
				<div class="form-group" style="margin-bottom:1rem">
					<label for="exampleDropdownFormEmail1"><b>아이디</b></label> 
					<input type="text" class="form-control" name="username" placeholder="Id">
				</div>
				<div class="form-group">
					<label for="exampleDropdownFormPassword1"><b>비밀번호</b></label> 
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
				<div style="margin-top:1rem;">
					<button type="button" class="btn btn-warning" style="background-color:#FBE9C3; float:left" onclick="location.href='/register/step1'">회원가입</button>
					<button type="submit" class="btn btn-primary" style="float:right">로그인</button>
				</div>
				
			</form>
			
		</div>
		<%@ include file="/view/includes/09_footer.jsp"%>
	</div>
	
</body>
</html>