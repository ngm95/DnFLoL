<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/view/includes/00_head.jsp"%>
<title>마이 페이지</title>
</head>
<body class="board-pages">

	<div class="container contents-wrap" style="height: 100%">
		<%@ include file="/view/includes/03_header.jsp"%>

		<div class="jumbotron">
			<button type="button" class="btn btn-style" onclick="location.href='/lol/findSummoner'" style="float: right; background-color: SkyBlue; color: white">계정 추가하기</button>
			<h3>
				<b>내 LOL 계정</b>
			</h3>
			<div class="jumbotron-board">
				<c:choose>
					<c:when test="${empty mylolChars}">
						<p>연동된 LOL 계정이 없습니다.</p>
					</c:when>
					<c:otherwise>
						<c:forEach var="chars" items="${mylolChars}">
							<div class="row">
								<form style="display: inline-block" action="${pageContext.request.contextPath}/lol/deleteSummoner/${chars.lcharName}" method="get">
									<p>
										계정명 : ${chars.lcharName}
										<button type="submit" class="btn btn-style" style="background-color: pink; color: white; margin-left: 15px">연동 해제</button>
								</form>
							</div>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</div>
		</div>

		<div class="jumbotron">
			<button type="button" class="btn btn-style" onclick="location.href='/dnf/findcharacter'" style="float: right; background-color: SkyBlue; color: white">계정 추가하기</button>
			<h3>
				<b>내 DNF 캐릭터</b>
			</h3>
			<div class="jumbotron-board">
				<c:choose>
					<c:when test="${empty mydnfChars}">
						<p>연동된 캐릭터가 없습니다.</p>
					</c:when>
					<c:otherwise>
						<c:forEach var="chars" items="${mydnfChars}">
							<div class="row">
								<div>
									<form style="display: inline-block" action="${pageContext.request.contextPath}/dnf/findcharacter/${chars.dcname}" method="get">
										<p>
											계정명 : ${chars.dcname}
											<button type="submit" class="btn btn-style" style="background-color: pink; color: white; margin-left: 15px">연동 해제</button>
									</form>
								</div>
							</div>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	
	
	<%@ include file="/view/includes/09_footer.jsp"%>
	</div>
	
</body>
</html>
