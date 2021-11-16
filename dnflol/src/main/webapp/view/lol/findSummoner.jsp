<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/view/includes/00_head.jsp"%>
<title>LoL 캐릭터 검색</title>
</head>
<body class="board-pages">

	<div class="container contents-wrap" style="height: 100%">
		<%@ include file="/view/includes/03_header.jsp"%>
		<jsp:include page="/view/includes/errorModal.jsp"></jsp:include>
		<div class="jumbotron">
			<h3>아이디 검색</h3>
			<form:form modelAttribute="summoner" action="/lol/findSummoner" method="post" style="margin-top:15px">
				<div class="input-group mb-3">
					<form:input type="text" class="form-control" path="name" placeholder="아이디"/>
					<form:button type="submit" class="btn btn-primary">검색</form:button>
				</div>
			</form:form>
		</div>

		<div class="jumbotron">
			<h3>검색 결과</h3>
			<div class="jumbotron-board">
				<c:choose>
					<c:when test="${empty summoner.name}">
						<p style="color:green"><b>검색된 결과가 없습니다.</b></p>
					</c:when>
					<c:when test="${empty summoner.summonerLevel}">
						<p style="color:red"><b>검색 중 오류가 발생했습니다.</b></p>
					</c:when>	
					<c:otherwise>
					
						<div class="d-flex">
							<div class="d-flex flex-row">
								<div class="d-flex flex-column">
									<img src="/lol/img/profileicon/${summoner.profileIconId}.png" width="80px">
								</div>
								<div class="d-flex flex-column" style="margin-left:20px; font-size:1.8em">
									<div class="d-flex flex-column">
										아이디 : ${summoner.name}, 레벨 : ${summoner.summonerLevel}
									</div>
									<div class="d-flex flex-column">
										티어 :
										<c:choose>
											<c:when test="${not empty leagueDto}">
												${leagueDto.tier} ${leagueDto.rank} ${leagueDto.leaguePoints}LP
											</c:when>
											<c:otherwise>
												unranked
											</c:otherwise>
										</c:choose>
									</div>
								</div>
							</div>
						</div>
						
						<form:form modelAttribute="summoner" action="/lol/addSummoner" method="post">
							<div class="col-xs-3">
								<form:input type="hidden" path="name" value="${summoner.name}"/>
								<button type="submit" class="btn btn-success" style="float:right">계정과 연동하기</button>
							</div>
						</form:form>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<%@ include file="/view/includes/09_footer.jsp"%>
	</div>
	
</body>
</html>
