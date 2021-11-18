<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/view/includes/00_head.jsp"%>
<title>LoL 계정 상세정보</title>
</head>
<body class="board-pages">

	<div class="container contents-wrap" style="height: 100%">
		<%@ include file="/view/includes/03_header.jsp"%>
		<jsp:include page="/view/includes/noticeModal.jsp"></jsp:include>
		<div class="jumbotron">
			<h2>계정 상세정보</h2>
			<div class="jumbotron-board">
				<div class="d-flex">
					<div class="d-flex flex-row">
						<div class="d-flex flex-column">
							<img src="/lol/img/profileicon/${summonerDto.profileIconId}.png" width="80px">
						</div>
						<div class="d-flex flex-column" style="margin-left: 20px; font-size: 1.8em">
							<div class="d-flex flex-column">아이디 : ${summonerDto.name}, 레벨 : ${summonerDto.summonerLevel}</div>
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
			</div>
			
			<h3 style="margin-top : 25px">최근 15게임 중 소환사의 협곡 ${match.totalGames}게임</h3>
			<div class="jumbotron-board">
				<table class="table table-striped">
					<thead>
						<tr>
							<th scope="col">총게임 횟수</th>
							<th scope="col">평균 K/D/A</th>
							<th scope="col">평균 가한 데미지</th>
							<th scope="col">평균 받은 데미지</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<th scope="row">${matchDetail.totalGames}</th>
							<td><fmt:formatNumber type="number" pattern="0.00" value="${matchDetail.totalKills/matchDetail.totalGames}" /> / <fmt:formatNumber type="number" pattern="0.00" value="${matchDetail.totalDeaths/matchDetail.totalGames}" /> / <fmt:formatNumber type="number" pattern="0.00" value="${matchDetail.totalAssists/matchDetail.totalGames}" /></td>
							<td><fmt:formatNumber type="number" pattern="0.00" value="${matchDetail.totalDamageToChampion/matchDetail.totalGames}" /></td>
							<td><fmt:formatNumber type="number" pattern="0.00" value="${matchDetail.totalDamageTaken/matchDetail.totalGames}" /></td>
						</tr>
					</tbody>
				</table>
				<table class="table table-striped">
					<thead>
						<tr>
							<th scope="col">게임 종류</th>
							<th scope="col">라인</th>
							<th scope="col">챔피언</th>
							<th scope="col">K/D/A</th>
							<th scope="col">가한 데미지</th>
							<th scope="col">받은 데미지</th>
							<th scope="col">상세보기</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="match" items="${matchList}">
							<tr>
								<td>${match.info.myInfo.gameType}</td>
								<td>${match.info.myInfo.individualPosition}</td>
								<td>${match.info.myInfo.championName}</td>
								<td>${match.info.myInfo.kills} / ${match.info.myInfo.deaths} / ${match.info.myInfo.assists}</td>
								<td>${match.info.myInfo.totalDamageDealtToChampions}</td>
								<td>${match.info.myInfo.totalDamageTaken}</td>
								<td>
								<c:choose>
									<c:when test="${match.info.myInfo.win eq true}">
										<button class="btn btn-success" type="button" onclick="window.open('/lol/matchDetail/${match.metadata.matchId}')">상세</button>
									</c:when>
									<c:otherwise>
										<button class="btn btn-danger" type="button" onclick="window.open('/lol/matchDetail/${match.metadata.matchId}')">상세</button>
									</c:otherwise>
								</c:choose></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				
			</div>
		</div>
		<%@ include file="/view/includes/09_footer.jsp"%>
	</div>
	
</body>
</html>
