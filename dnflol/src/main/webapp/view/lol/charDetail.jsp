<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/view/includes/00_head.jsp"%>
<title>LoL 계정 상세정보</title>
</head>
<body class="board-pages">

	<div class="container contents-wrap" style="height: 100%">
		<%@ include file="/view/includes/03_header.jsp"%>
		<jsp:include page="/view/includes/errorModal.jsp"></jsp:include>
		<div class="jumbotron">
			<h3>계정 상세정보</h3>
			<div class="jumbotron-board">
				<pre style="font-size:3.5rem">${lcharDto.lcharName}</pre>
				<c:choose>
					<c:when test="${'IRON' eq leagueDto.tier}">
						<img src="/lol/img/ranked-emblems/Emblem_Iron.png" style="width: 15%"> <pre style="font-size:3.0rem">${leagueDto.tier} ${leagueDto.rank}, ${leagueDto.leaguePoints}p</pre>
					</c:when>
					<c:when test="${'BRONZE' eq leagueDto.tier}">
						<img src="/lol/img/ranked-emblems/Emblem_Bronze.png" style="width: 15%"> <pre style="font-size:3.0rem">${leagueDto.tier} ${leagueDto.rank}, ${leagueDto.leaguePoints}p</pre>
					</c:when>
					<c:when test="${'SILVER' eq leagueDto.tier}">
						<img src="/lol/img/ranked-emblems/Emblem_Silver.png" style="width: 15%"> <pre style="font-size:3.0rem">${leagueDto.tier} ${leagueDto.rank}, ${leagueDto.leaguePoints}p</pre>
					</c:when>
					<c:when test="${'GOLD' eq leagueDto.tier}">
						<img src="/lol/img/ranked-emblems/Emblem_Gold.png" style="width: 15%"> <pre style="font-size:3.0rem">${leagueDto.tier} ${leagueDto.rank}, ${leagueDto.leaguePoints}p</pre>
					</c:when>
					<c:when test="${'PLATINUM' eq leagueDto.tier}">
						<img src="/lol/img/ranked-emblems/Emblem_Platinum.png" style="width: 15%"> <pre style="font-size:3.0rem">${leagueDto.tier} ${leagueDto.rank}, ${leagueDto.leaguePoints}p</pre>
					</c:when>
					<c:when test="${'DIAMOND' eq leagueDto.tier}">
						<img src="/lol/img/ranked-emblems/Emblem_Diamond.png" style="width: 15%"> <pre style="font-size:3.0rem">${leagueDto.tier} ${leagueDto.rank}, ${leagueDto.leaguePoints}p</pre>
					</c:when>
					<c:when test="${'MASTER' eq leagueDto.tier}">
						<img src="/lol/img/ranked-emblems/Emblem_Master.png" style="width: 15%"> <pre style="font-size:3.0rem">${leagueDto.tier} ${leagueDto.rank}, ${leagueDto.leaguePoints}p</pre>
					</c:when>
					<c:when test="${'GRANDMASTER' eq leagueDto.tier}">
						<img src="/lol/img/ranked-emblems/Emblem_Grandmaster.png" style="width: 15%"> <pre style="font-size:3.0rem">${leagueDto.tier} ${leagueDto.rank}, ${leagueDto.leaguePoints}p</pre>
					</c:when>
					<c:when test="${'CHALLENGER' eq leagueDto.tier}">
						<img src="/lol/img/ranked-emblems/Emblem_Challenger.png" style="width: 15%"> <pre style="font-size:3.0rem">${leagueDto.tier} ${leagueDto.rank}, ${leagueDto.leaguePoints}p</pre>
					</c:when>
				</c:choose>
			</div>
				
			<h3 style="margin-top : 25px">최근 ${rankDetail.totalGames + normalDetail.totalGames}게임 중 소환사의 협곡</h3>
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
							<th scope="row">${rankDetail.totalGames + normalDetail.totalGames}</th>
							<td>${(rankDetail.totalKills + normalDetail.totalKills)/(rankDetail.totalGames + normalDetail.totalGames)} / ${(rankDetail.totalDeaths + normalDetail.totalDeaths)/(rankDetail.totalGames + normalDetail.totalGames)} / ${(rankDetail.totalAssists + normalDetail.totalAssists)/(rankDetail.totalGames + normalDetail.totalGames)}</td>
							<td>${(rankDetail.totalDamageToChampion + normalDetail.totalDamageToChampion)/(rankDetail.totalGames + normalDetail.totalGames)}</td>
							<td>${(rankDetail.totalDamageTaken + normalDetail.totalDamageTaken)/(rankDetail.totalGames + normalDetail.totalGames)}</td>
						</tr>
					</tbody>
				</table>

				<div class="accordion" id="accordionExample" style="margin-top : 15px">
					<div class="accordion-item">
						<h2 class="accordion-header" id="headingOne">
							<button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne">랭크 게임</button>
						</h2>
						<div id="collapseOne" class="accordion-collapse collapse show" aria-labelledby="headingOne" data-bs-parent="#accordionExample">
							<div class="accordion-body">
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
											<th scope="row">${rankDetail.totalGames}</th>
											<td>${rankDetail.totalKills/rankDetail.totalGames}/ ${rankDetail.totalDeaths/rankDetail.totalGames} / ${rankDetail.totalAssists/rankDetail.totalGames}</td>
											<td>${rankDetail.totalDamageToChampion/rankDetail.totalGames}</td>
											<td>${rankDetail.totalDamageTaken/rankDetail.totalGames}</td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					<div class="accordion-item">
						<h2 class="accordion-header" id="headingTwo">
							<button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">일반 게임</button>
						</h2>
						<div id="collapseTwo" class="accordion-collapse collapse" aria-labelledby="headingTwo" data-bs-parent="#accordionExample">
							<div class="accordion-body">
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
											<th scope="row">${normalDetail.totalGames}</th>
											<td>${normalDetail.totalKills/normalDetail.totalGames}/ ${normalDetail.totalDeaths/normalDetail.totalGames} / ${normalDetail.totalAssists/normalDetail.totalGames}</td>
											<td>${normalDetail.totalDamageToChampion/normalDetail.totalGames}</td>
											<td>${normalDetail.totalDamageTaken/normalDetail.totalGames}</td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
				
			</div>
		</div>
		<%@ include file="/view/includes/09_footer.jsp"%>
	</div>
	
</body>
</html>
