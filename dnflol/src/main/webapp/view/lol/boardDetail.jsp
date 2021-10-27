<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/view/includes/00_head.jsp"%>
<title>게시글 세부내용</title>
</head>
<body class="board-pages">

	<div class="container" style="height: 100%">
		<%@ include file="/view/includes/03_header.jsp"%>
		<div class="jumbotron">
			<h2>제목 : <b>${lgroupDto.lgroupName}</b></h2>
			<h3>작성자 : ${lgroupDto.lgroupOwner}</h3>
			<h3>게임 타입 : ${lgroupDto.lgroupType}</h3>
			<h3>인원 수 : ${fn:length(acceptedList)+1} / ${lgroupDto.lgroupMax}</h3>
			
			<c:choose>
				<c:when test="${empty acceptedList}">
					<p>아직 수락된 신청이 없습니다.</p>
				</c:when>
				<c:otherwise>
					<c:forEach var="accepted" items="acceptedList">
						<div style="display:inline-block">
							<form style="display: inline-block" action="${pageContext.request.contextPath}//lol/matchAndLeague/${accepted.lcharName}" method="get">
								<p>계정명 : ${accepted.lcharName} 
								<input type="submit" value="상세 정보">
							</form>
						</div>
					</c:forEach>
				</c:otherwise>
			</c:choose>
			<h4>설명 : ${lgroupDto.lgroupDetail}</h4>
		</div>
		
		<div>
			<div class="jumbotron">
				<h3>내 LOL 계정</h3>
				<c:choose>
					<c:when test="${empty mylolChars}">
						<p>연동된 LOL 계정이 없습니다.</p>
						<a href="/user/myPage">연동하러 가기</a>
					</c:when>
					<c:otherwise>
						<c:forEach var="chars" items="${mylolChars}">
							<div>
								<form:form modelAttribute="applyForm" style="display: inline-block" action="${pageContext.request.contextPath}/lol/submit/" method="post">
									<p>계정명 : ${chars.lcharName} 
									<input type="hidden" id="lgroupId" name="lgroupId" value="${lgroupDto.lgroupId}">
									<input type="hidden" id="lcharName" name="lcharName" value="${chars.lcharName}">
									<input type="submit" value="신청하기">
								</form:form>
							</div>
						</c:forEach>
						<c:if test="${not empty leae}">
							<p style="color:red">${leae}은/는 이미 신청한 적이 있는 계정입니다.</p>
						</c:if>
						<c:if test="${not empty applyResult}">
							<p style="color:green">현재 게시글에 ${applyResult}이/가 정상적으로 신청했습니다.</p>
						</c:if>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>
	<%@ include file="/view/includes/09_footer.jsp"%>
</body>
</html>
