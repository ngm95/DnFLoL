<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/view/includes/00_head.jsp"%>
<title>마이 페이지</title>
</head>
<body class="board-pages">

	<div class="container" style="height: 100%">
		<%@ include file="/view/includes/03_header.jsp"%>
		
		<div>
			<div class="board-pages">
				<div class="jumbotron">
					<h2><b>내 LOL 계정</b></h2>
					<c:choose>
						<c:when test="${empty mylolChars}">
							<p>연동된 LOL 계정이 없습니다.</p>
						</c:when>
						<c:otherwise>
							<c:forEach var="chars" items="${mylolChars}">
							<div>
								
								<form style="display:inline-block" action="${pageContext.request.contextPath}/lol/deleteSummoner/${chars.lcharName}" method="get">
									<p>계정명 : ${chars.lcharName}
									<input type="submit" value="연동 해제">
								</form>
							</div>
							</c:forEach>
						</c:otherwise>
					</c:choose>
					<div class="col-xs-3">
						<form action="${pageContext.request.contextPath}/lol/findSummoner" method="get">
							<input type="submit" value="계정 추가하기">
						</form>
					</div>
				</div>
			</div>
			
			<div class="board-pages">
				<div class="jumbotron">
					<h2><b>내가 작성한 LOL 게시글</b></h2>
					<c:choose>
						<c:when test="${empty mylolGroups}">
							<p>작성한 LOL 게시글이 없습니다.</p>
						</c:when>
						<c:otherwise>
							<c:forEach var="lgroup" items="${mylolGroups}">
								<h3><a href="/lol/boardDetail/${lgroup.lgroupId}">제목 : ${lgroup.lgroupName}</a></h3>
								<h4>게시글 작성자 : ${lgroup.lgroupOwner}, 게임 종류 : ${lgroup.lgroupType}</h4>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
			
		</div>
	</div>
</body>
</html>
