<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/view/includes/00_head.jsp"%>
<title>캐릭터 검색</title>
</head>
<body class="board-pages">

	<div class="container contents-wrap" style="height: 100%">
		<%@ include file="/view/includes/03_header.jsp"%>
		<div class="jumbotron">
			<form:form modelAttribute="summoner" action="/lol/findSummoner" method="post">
				<div class="form-group has-feedback">
					<label for="uid">아이디</label>
					<form:input type="text" class="form-control" placeholder="ID" path="name" id="name" />
					<span class="glyphicon glyphicon-user form-control-feedback"></span>
				</div>
				<div class="row">
					<div class="col-xs-8">
						<button type="submit" class="btn btn-style" style="background-color:blue; color:white">검색</button>
					</div>
				</div>
			</form:form>
		</div>

		<div>
			<h3>검색 결과</h3>
			<c:choose>
				<c:when test="${empty summoner.name}">
					<p>검색된 결과가 없습니다.</p>
				</c:when>
				<c:when test="${empty summoner.summonerLevel}">
					<p>검색 중 오류가 발생했습니다.</p>
				</c:when>	
				<c:otherwise>
					<c:choose>
						<c:when test="${'IRON' eq leagueDto.tier}">
							<img src="/lol/img/ranked-emblems/Emblem_Iron.png" style="width:15%">
						</c:when>
						<c:when test="${'BRONZE' eq leagueDto.tier}">
							<img src="/lol/img/ranked-emblems/Emblem_Bronze.png" style="width:15%">
						</c:when>
						<c:when test="${'SILVER' eq leagueDto.tier}">
							<img src="/lol/img/ranked-emblems/Emblem_Silver.png" style="width:15%">
						</c:when>
						<c:when test="${'GOLD' eq leagueDto.tier}">
							<img src="/lol/img/ranked-emblems/Emblem_Gold.png" style="width:15%">
						</c:when>
						<c:when test="${'PLATINUM' eq leagueDto.tier}">
							<img src="/lol/img/ranked-emblems/Emblem_Platinum.png" style="width:15%">
						</c:when>
						<c:when test="${'DIAMOND' eq leagueDto.tier}">
							<img src="/lol/img/ranked-emblems/Emblem_Diamond.png" style="width:15%">
						</c:when>
						<c:when test="${'MASTER' eq leagueDto.tier}">
							<img src="/lol/img/ranked-emblems/Emblem_Master.png" style="width:15%">
						</c:when>
						<c:when test="${'GRANDMASTER' eq leagueDto.tier}">
							<img src="/lol/img/ranked-emblems/Emblem_Grandmaster.png" style="width:15%">
						</c:when>
						<c:when test="${'CHALLENGER' eq leagueDto.tier}">
							<img src="/lol/img/ranked-emblems/Emblem_Challenger.png" style="width:15%">
						</c:when>
					</c:choose>
					아이디 : ${summoner.name}, 레벨 : ${summoner.summonerLevel}, 티어 : ${leagueDto.tier} ${leagueDto.rank}
					<form:form modelAttribute="summoner" action="/lol/addSummoner" method="post">
						<div class="col-xs-3">
							<form:input type="hidden" path="name" value="${summoner.name}"/>
							<button type="submit" class="btn btn-style" style="float:right">계정과 연동하기</button>
						</div>
					</form:form>
				</c:otherwise>
			</c:choose>
		</div>
		<%@ include file="/view/includes/09_footer.jsp"%>
	</div>
	
</body>
</html>
