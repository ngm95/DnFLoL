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
		<c:if test="${not empty error}">
			<jsp:include page="/view/includes/errorModal.jsp"></jsp:include>
		</c:if>
		
		<div class="jumbotron">
			<h3>캐릭터 검색</h3>
			<form:form modelAttribute="character" action="/dnf/findcharacter" method="post">
				<div class="input-group">
					<form:select class="form-select" path="serverId">
						<form:option value="all">전체</form:option>
						<form:option value="anton">안톤</form:option>
						<form:option value="bakal">바칼</form:option>
						<form:option value="cain">카인</form:option>
						<form:option value="casillas">카시야스</form:option>
						<form:option value="diregie">디레지에</form:option>
						<form:option value="hilder">힐더</form:option>
						<form:option value="prey">프레이</form:option>
						<form:option value="siroco">시로코</form:option>
					</form:select>
					<form:input class="form-control" type="text" path="characterName" placeholder="캐릭터 이름"/>
					<form:button class="btn btn-outline-secondary" type="submit">검색하기</form:button>
				</div>
			</form:form>
		</div>

		<div class="jumbotron">
			<h3>검색 결과</h3>
			<div class="jumbotron-board">
				<c:choose>
					<c:when test="${empty characters}">
						<p style="color: green">
							<b>검색된 결과가 없습니다.</b>
						</p>
					</c:when>
					<c:otherwise>
						<div class="row row-cols-6">
							<c:forEach var="chars" items="${characters}">

								<div class="col-6 col-md-4">
									<div class="m-4">
										<form:form modelAttribute="character" action="/dnf/addcharacter" method="post">
											<div class="card" style="margin-bottom: 25px">
												<form:input type="hidden" path="characterName" value="${chars.characterName}" />
												<form:input type="hidden" path="characterId" value="${chars.characterId}" />
												<form:input type="hidden" path="serverId" value="${chars.serverId}" />
												<button type="submit" class="btn btn-success" style="background-color: white;">
													<img src="https://img-api.neople.co.kr/df/servers/${chars.serverId}/characters/${chars.characterId}?zoom=1" class="card-img-top">
												</button>
												<div class="card-body">
													<p class="card-text">${chars.serverId}<br>${chars.characterName}</p>
												</div>
											</div>
										</form:form>
									</div>
								</div>
							</c:forEach>
						</div>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<%@ include file="/view/includes/09_footer.jsp"%>
	</div>
	
</body>
</html>