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
		<jsp:include page="/view/includes/noticeModal.jsp"></jsp:include>
		
		<div class="jumbotron" style="padding-bottom: 40px">
			<div class="row">
				<div class="col">
					<h3>로그인 양식</h3>
				</div>
				<div class="col">
					<button type="button" class="btn btn-warning" style="float:right" onclick="location.href='/register/step1'">회원가입</button>
				</div>
			</div>
			
			<div class="jumbotron-board">
				<form class="px-4 py-3" action="/security/login" method="post">
					<div class="form-group" style="margin-bottom: 1rem">
						<label for="exampleDropdownFormEmail1"><b>아이디</b></label> 
						<input type="text" class="form-control" name="username" placeholder="Id">
					</div>
					<div class="form-group">
						<label for="exampleDropdownFormPassword1"><b>비밀번호</b></label> 
						<input type="password" class="form-control" name="password" placeholder="Password">
					</div>
					<c:if test="${not empty ERRORMSG}">
						<font color="red">
							<p>${ERRORMSG}</p>
						</font>
					</c:if>
					<input name="${_csrf.parameterName}" type="hidden" value="${_csrf.token}" />
					<div style="margin-top: 1rem;">
						<button type="submit" class="btn btn-primary" style="float: right">로그인</button>
					</div>
				</form>
			</div>
		</div>
		<%@ include file="/view/includes/09_footer.jsp"%>
	</div>
	
</body>
</html>