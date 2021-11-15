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
		<jsp:include page="/view/includes/errorModal.jsp"></jsp:include>
		<div class="jumbotron">
			<h2><b>${lcharDto.lcharName}</b> 계정 상세정보</h2>
			<h3 style="margin-top : 25px">최근 20게임 중 소환사의 협곡 ${rankDetail.totalGames + normalDetail.totalGames}게임</h3>
			<div class="jumbotron-board">
				<div class="accordion" id="accordionExample" style="margin-top : 15px">
					<div class="accordion-item">
						<h2 class="accordion-header" id="headingTotal">
							<button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#collapseTotal" aria-expanded="true" aria-controls="collapseTotal">전체 게임</button>
						</h2>
						<div id="collapseTotal" class="accordion-collapse collapse show" aria-labelledby="headingTotal" data-bs-parent="#accordionExample">
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
											<th scope="row">${rankDetail.totalGames + normalDetail.totalGames}</th>
											<td><fmt:formatNumber type="number" pattern="0.00" value="${(rankDetail.totalKills + normalDetail.totalKills)/(rankDetail.totalGames + normalDetail.totalGames)}" /> / <fmt:formatNumber type="number" pattern="0.00" value="${(rankDetail.totalDeaths + normalDetail.totalDeaths)/(rankDetail.totalGames + normalDetail.totalGames)}" /> / <fmt:formatNumber type="number" pattern="0.00"
													value="${(rankDetail.totalAssists + normalDetail.totalAssists)/(rankDetail.totalGames + normalDetail.totalGames)}" /></td>
											<td><fmt:formatNumber type="number" pattern="0.00" value="${(rankDetail.totalDamageToChampion + normalDetail.totalDamageToChampion)/(rankDetail.totalGames + normalDetail.totalGames)}" /></td>
											<td><fmt:formatNumber type="number" pattern="0.00" value="${(rankDetail.totalDamageTaken + normalDetail.totalDamageTaken)/(rankDetail.totalGames + normalDetail.totalGames)}" /></td>
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
										</tr>
									</thead>
									<tbody>
										<c:forEach var="total" items="${totalQ}">
											<tr>
												<td>${total.gameType}</td>
												<td>${total.individualPosition}</td>
												<td>${total.championName}</td>
												<td>${total.kills} / ${total.deaths} / ${total.assists}</td>
												<td>${total.totalDamageDealtToChampions}</td>
												<td>${total.totalDamageTaken}</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					<div class="accordion-item">
						<h2 class="accordion-header" id="headingRank">
							<button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseRank" aria-expanded="false" aria-controls="collapseRank">랭크 게임</button>
						</h2>
						<div id="collapseRank" class="accordion-collapse collapse" aria-labelledby="headingRank" data-bs-parent="#accordionExample">
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
											<td><fmt:formatNumber type="number" pattern="0.00" value="${rankDetail.totalKills/rankDetail.totalGames}"/> / <fmt:formatNumber type="number" pattern="0.00" value="${rankDetail.totalDeaths/rankDetail.totalGames}"/> / <fmt:formatNumber type="number" pattern="0.00" value="${rankDetail.totalAssists/rankDetail.totalGames}"/></td>
											<td><fmt:formatNumber type="number" pattern="0.00" value="${rankDetail.totalDamageToChampion/rankDetail.totalGames}"/></td>
											<td><fmt:formatNumber type="number" pattern="0.00" value="${rankDetail.totalDamageTaken/rankDetail.totalGames}"/></td>
										</tr>
									</tbody>
								</table>
								<table class="table table-striped">
									<thead>
										<tr>
											<th scope="col">라인</th>
											<th scope="col">챔피언</th>
											<th scope="col">K/D/A</th>
											<th scope="col">가한 데미지</th>
											<th scope="col">받은 데미지</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="rank" items="${rankQ}">
											<tr>
												<td>${rank.individualPosition}</td>
												<td>${rank.championName}</td>
												<td>${rank.kills} / ${rank.deaths} / ${rank.assists}</td>
												<td>${rank.totalDamageDealtToChampions}</td>
												<td>${rank.totalDamageTaken}</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					<div class="accordion-item">
						<h2 class="accordion-header" id="headingNormal">
							<button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseNormal" aria-expanded="false" aria-controls="collapseNormal">일반 게임</button>
						</h2>
						<div id="collapseNormal" class="accordion-collapse collapse" aria-labelledby="headingNormal" data-bs-parent="#accordionExample">
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
											<td><fmt:formatNumber type="number" pattern="0.00" value="${normalDetail.totalKills/normalDetail.totalGames}"/> / <fmt:formatNumber type="number" pattern="0.00" value="${normalDetail.totalDeaths/normalDetail.totalGames}"/> / <fmt:formatNumber type="number" pattern="0.00" value="${normalDetail.totalAssists/normalDetail.totalGames}"/></td>
											<td><fmt:formatNumber type="number" pattern="0.00" value="${normalDetail.totalDamageToChampion/normalDetail.totalGames}"/></td>
											<td><fmt:formatNumber type="number" pattern="0.00" value="${normalDetail.totalDamageTaken/normalDetail.totalGames}"/></td>
										</tr>
									</tbody>
								</table>
								<table class="table table-striped">
									<thead>
										<tr>
											<th scope="col">라인</th>
											<th scope="col">챔피언</th>
											<th scope="col">K/D/A</th>
											<th scope="col">가한 데미지</th>
											<th scope="col">받은 데미지</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="normal" items="${normalQ}">
											<tr>
												<td>${normal.individualPosition}</td>
												<td>${normal.championName}</td>
												<td>${normal.kills} / ${normal.deaths} / ${normal.assists}</td>
												<td>${normal.totalDamageDealtToChampions}</td>
												<td>${normal.totalDamageTaken}</td>
											</tr>
										</c:forEach>
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
