<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
			<c:choose>
				<c:when test="${empty summonerDto.name}">
					<p>검색된 결과가 없습니다.</p>
				</c:when>
				<c:when test="${empty summonerDto.summonerLevel}">
					<p>검색 중 오류가 발생했습니다.</p>
				</c:when>	
				<c:otherwise>
					<h6>검색 결과</h6>
					<p>아이디 : ${summonerDto.name}, 레벨 : ${summonerDto.summonerLevel}, 티어 : ${leagueDto.tier} ${leagueDto.rank}</p>
					<form action="/lol/addSummoner" method="GET">
						<div class="col-xs-3">
							<button type="submit" class="btn btn-style">계정과 연동하기</button>
						</div>
					</form>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
	<%@ include file="/view/includes/09_footer.jsp"%>
</body>
</html>
