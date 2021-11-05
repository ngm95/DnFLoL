<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/view/includes/00_head.jsp"%>

<title>LoL 글 작성하기</title>
</head>
<body class="board-pages">

	<div class="container contents-wrap" style="height: 100%">
		<%@ include file="/view/includes/03_header.jsp"%>

		<div class="board-pages">

			<div class="jumbotron" style="padding-bottom:45px">
				
				<c:choose>
					<c:when test="${empty mylolChars}">
						<h3><b>내 LOL 계정 선택</b></h3>
						<p>연동된 LOL 계정이 없어서 게시글 작성이 불가능합니다.</p>
						<a href="/user/myPage">연동하러 가기</a>
					</c:when>
					
					<c:otherwise>
						<h3><b>게시글 작성</b></h3>
						
						<form:form modelAttribute="post" action="/lol/board/newPostPOST" method="post">

							<div class="input-group" style="margin-bottom:15px; margin-top:10px">
								<form:input class="form-control" type="text" path="lgroupName" placeholder="제목" />
							</div>
							<div class="input-group" style="margin-bottom:15px">
								<form:select class="form-select" path="lgroupOwner">
									<c:forEach var="chars" items="${mylolChars}">
										<form:option value="${chars.lcharName}">${chars.lcharName}</form:option>
									</c:forEach>
								</form:select>
								<form:select class="form-select" path="lgroupType">
									<form:option value="듀오랭크">듀오랭크</form:option>
									<form:option value="자유랭크">자유랭크</form:option>
									<form:option value="일반게임">일반게임</form:option>
								</form:select>
							</div>
							<div class="input-group" style="margin-bottom:5px">
								<form:textarea class="form-control" type="text" rows="5" path="lgroupDetail" placeholder="게시글 설명"/>
							</div>
							<button type="submit" class="btn btn-primary" style="float:right">작성</button>

						</form:form>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<%@ include file="/view/includes/09_footer.jsp"%>
	</div>
	
</body>
</html>
