<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/view/includes/00_head.jsp"%>
<title>Dnf 캐릭터 검색</title>
</head>
<body class="board-pages">

	<div class="container contents-wrap" style="height: 100%">
		<%@ include file="/view/includes/03_header.jsp"%>
		<div class="jumbotron">
			<h3>캐릭터 검색</h3>
			<form:form modelAttribute="character" action="/dnf/findcharacter" method="post" style="margin-top:15px">
				<div class="input-group mb-3">
					<form:input type="text" class="form-control" path="characterName" placeholder="캐릭터 이름"/>
					<form:button type="submit" class="btn btn-primary">검색</form:button>
				</div>
			</form:form>
		</div>

		<div class="jumbotron">
			<h3>검색 결과</h3>
			<div class="jumbotron-board">
				<c:choose>
					<c:when test="${empty result.characterName}">
						<p style="color:green"><b>검색된 결과가 없습니다.</b></p>
					</c:when>	
					<c:otherwise>
						<c:forEach var="chars" items="${characters}">

							<img src="https://img-api.neople.co.kr/df/servers/${chars.serverId}/characters/${chars.characterName}?zoom=1" style="width: 15%">
							
							이름 : ${chars.characterName}, 서버 : ${chars.serverId}
							
							<form:form modelAttribute="character" action="/dnf/addcharacter" method="post">
									<div class="col-xs-3">
										<form:input type="hidden" path="characterName" value="${chars.characterName}" />
										<form:input type="hidden" path="characterId" value="${chars.characterId}"/>
										<form:input type="hidden" path="serverId" value="${chars.serverId}"/>
										<button type="submit" class="btn btn-success" style="float: right">계정과 연동하기</button>
									</div>
							</form:form>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<%@ include file="/view/includes/09_footer.jsp"%>
	</div>
	
</body>
</html>