<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/view/includes/00_head.jsp"%>

<title>글 작성하기</title>
</head>
<body class="board-pages">

	<div class="container" style="height: 100%">
		<%@ include file="/view/includes/03_header.jsp"%>

		<div class="board-pages">

			<div class="jumbotron">
				<h3><b>내 LOL 계정 선택</b></h3>
				<c:choose>
					<c:when test="${empty mylolChars}">
						<p>연동된 LOL 계정이 없어서 게시글 작성이 불가능합니다.</p>
						<a href="/user/myPage">연동하러 가기</a>
					</c:when>
					
					<c:otherwise>
						<form:form modelAttribute="post" action="/lol/board/newPostPOST" method="post">
							<c:forEach var="chars" items="${mylolChars}">
								<input type="radio" name="lgroupOwner" value="${chars.lcharName}" style="padding-right:20px" checked>${chars.lcharName}
							</c:forEach>

							<div class="form-group has-feedback">
								<form:input type="text" class="form-control" placeholder="제목" path="lgroupName" id="lgroupName" />
								<span class="glyphicon glyphicon-user form-control-feedback"></span>
							</div>

							<div>
								<input type="radio" name="lgroupType" value="듀오랭크" style="padding-right:20px" checked>듀오랭크 
								<input type="radio" name="lgroupType" value="자유랭크" style="padding-right:20px">자유랭크 
								<input type="radio" name="lgroupType" value="일반게임" style="padding-right:20px">일반게임
							</div>

							<div class="form-group has-feedback">
								<form:input type="text" class="form-control" placeholder="내용" path="lgroupDetail" id="lgroupDetail" />
								<span class="glyphicon glyphicon-lock form-control-feedback"></span>
							</div>

							<div class="row">
								<div class="col-xs-1">
									<button type="submit" class="btn btn-style">작성하기</button>
								</div>
							</div>
						</form:form>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>
	<%@ include file="/view/includes/09_footer.jsp"%>
</body>
</html>
