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
				<h2><b>LOL 게시글 작성</b></h2>
				<c:choose>
					<c:when test="${empty mylolChars}">
						<p>연동된 LOL 계정이 없어서 게시글 작성이 불가능합니다.</p>
						<a href="/user/myPage">계정 연동하러 가기</a>
					</c:when>
					
					<c:otherwise>
						<form:form modelAttribute="post" action="/lol/board/newPostPOST" method="post">
							<div class="form-group">
								<div>
									<form:label path="lgroupName">게시글 제목</form:label>
									<form:input type="text" class="form-control" placeholder="제목" path="lgroupName" style="margin-bottom:15px"/>	
								</div>
								
								<div>
									<form:label path="lgroupOwner">내 계정 선택</form:label>
									<form:select path="lgroupOwner" style="margin-right:30px; padding-left:5px; padding-right:5px; margin-bottom:15px">
										<c:forEach var="chars" items="${mylolChars}">
											<form:option value="${chars.lcharName}">${chars.lcharName}</form:option>
										</c:forEach>
									</form:select>

									<form:label path="lgroupType">게임 타입 선택</form:label>
									<form:select path="lgroupType" style="padding-left:5px; padding-right:5px">
										<form:option value="듀오랭크">듀오랭크</form:option>
										<form:option value="자유랭크">자유랭크</form:option>
										<form:option value="일반게임">일반게임</form:option>
									</form:select>
								</div>
								
								<div>
									<form:label path="lgroupDetail">게시글 설명</form:label>
									<form:textarea class="form-control" rows="5" path="lgroupDetail"></form:textarea>
								</div>
								
								<form:button type="submit" style="margin-top:15px">작성하기</form:button>
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
