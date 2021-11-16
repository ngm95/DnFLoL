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
<title>LoL 게임 상세정보</title>
</head>
<body class="board-pages">

	<div class="container contents-wrap" style="height: 100%">
		<%@ include file="/view/includes/03_header.jsp"%>
		<jsp:include page="/view/includes/errorModal.jsp"></jsp:include>
		<div class="jumbotron">
			<div class="jumbotron-board">
				<h3>게임 시간 : ${gameDuration}</h3>
				<div class="card" style="margin-top:30px">
					<br/>
					<c:choose>
						<c:when test="${match.info.participants[0].win eq true}">
							<h3 class="card-title" style="color:blue">&nbsp;<b>승리</b></h3>
						</c:when>
						<c:otherwise>
							<h3 class="card-title" style="color:red">&nbsp;<b>패배</b></h3>
						</c:otherwise>
					</c:choose>
					
					<div class="card-body">
						<table class="table table-striped">
							<thead>
								<tr>
									<th scope="col">계정 이름</th>
									<th scope="col">챔피언 정보</th>
									<th scope="col">K/D/A</th>
									<th scope="col">준 데미지</th>
									<th scope="col">받은 데미지</th>
									<th scope="col">CS</th>
									<th scope="col">Total Gold</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach begin="0" end="4" step="1" varStatus="status">
									<tr>
										<td>
											${match.info.participants[status.index].summonerName}
											
										</td>
										<td>
											<div class="d-flex flex-row">
												<div class="d-flex flex-column">
													<div class="d-flex flex-column">
														<img src="/lol/img/spell/${match.info.participants[status.index].summoner1Id}.png" width="27px"> <img src="/lol/img/spell/${match.info.participants[status.index].summoner2Id}.png" width="27px">
													</div>
												</div>
												<div class="d-flex flex-column">
													<div class="d-flex flex-column">
														<img src="/lol/img/perk-images/styles/${match.info.participants[status.index].perks.styles[0].selections[0].perk}.png" width="30px"> <img src="/lol/img/perk-images/styles/${match.info.participants[status.index].perks.styles[1].style}.png" width="25px">
													</div>
												</div>
												<div class="d-flex flex-column">
												<div class="d-flex flex-column">
													<c:choose>
														<c:when test="${match.info.participants[0].win eq true}">
															<span class="badge rounded-pill bg-primary">${match.info.participants[status.index].champLevel}</span>
														</c:when>
														<c:otherwise>
															<span class="badge rounded-pill bg-danger">${match.info.participants[status.index].champLevel}</span>
														</c:otherwise>
													</c:choose>
												</div>
											</div>
												<div class="d-flex flex-column">
													<img src="/lol/img/champion/${match.info.participants[status.index].championName}.png" width="55px">
												</div>
												<div class="d-flex flex-column" style="margin-left: 5px; margin-top: 5px">
													<div class="d-flex flex-row">
														<img src="/lol/img/item/${match.info.participants[status.index].item0}.png" width="45px"> <img src="/lol/img/item/${match.info.participants[status.index].item1}.png" width="45px"> <img src="/lol/img/item/${match.info.participants[status.index].item2}.png" width="45px"> <img src="/lol/img/item/${match.info.participants[status.index].item3}.png" width="45px"> <img src="/lol/img/item/${match.info.participants[status.index].item4}.png" width="45px"> <img
															src="/lol/img/item/${match.info.participants[status.index].item5}.png" width="45px"> <img src="/lol/img/item/${match.info.participants[status.index].item6}.png" width="45px">
													</div>
												</div>
											</div>
										</td>
										<td>${match.info.participants[status.index].kills} / ${match.info.participants[status.index].deaths} / ${match.info.participants[status.index].assists}</td>
										<td>${match.info.participants[status.index].totalDamageDealtToChampions}</td>
										<td>${match.info.participants[status.index].totalDamageTaken}</td>
										<td>${match.info.participants[status.index].totalMinionsKilled}</td>
										<td>${match.info.participants[status.index].goldEarned}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>

			<div class="card">
				<br />
				<c:choose>
					<c:when test="${match.info.participants[5].win eq true}">
						<h3 class="card-title" style="color: blue">
							&nbsp;<b>승리</b>
						</h3>
					</c:when>
					<c:otherwise>
						<h3 class="card-title" style="color: red">
							&nbsp;<b>패배</b>
						</h3>
					</c:otherwise>
				</c:choose>

				<div class="card-body">
					<table class="table table-striped">
						<thead>
							<tr>
								<th scope="col">계정 이름</th>
								<th scope="col">챔피언 정보</th>
								<th scope="col">K/D/A</th>
								<th scope="col">준 데미지</th>
								<th scope="col">받은 데미지</th>
								<th scope="col">CS</th>
								<th scope="col">Total Gold</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach begin="5" end="9" step="1" varStatus="status">
								<tr>
									<td>${match.info.participants[status.index].summonerName}
									</td>
									<td>
										<div class="d-flex flex-row">
											<div class="d-flex flex-column">
												<div class="d-flex flex-column">
													<img src="/lol/img/spell/${match.info.participants[status.index].summoner1Id}.png" width="27px"> <img src="/lol/img/spell/${match.info.participants[status.index].summoner2Id}.png" width="27px">
												</div>
											</div>
											<div class="d-flex flex-column">
												<div class="d-flex flex-column">
													<img src="/lol/img/perk-images/styles/${match.info.participants[status.index].perks.styles[0].selections[0].perk}.png" width="30px"> <img src="/lol/img/perk-images/styles/${match.info.participants[status.index].perks.styles[1].style}.png" width="25px">
												</div>
											</div>
											<div class="d-flex flex-column">
												<div class="d-flex flex-column">
													<c:choose>
														<c:when test="${match.info.participants[5].win eq true}">
															<span class="badge rounded-pill bg-primary">${match.info.participants[status.index].champLevel}</span>
														</c:when>
														<c:otherwise>
															<span class="badge rounded-pill bg-danger">${match.info.participants[status.index].champLevel}</span>
														</c:otherwise>
													</c:choose>
												</div>
											</div>
											<div class="d-flex flex-column">
												<img src="/lol/img/champion/${match.info.participants[status.index].championName}.png" width="55px">
											</div>
											<div class="d-flex flex-column" style="margin-left: 5px; margin-top: 5px">
												<div class="d-flex flex-row">
													<img src="/lol/img/item/${match.info.participants[status.index].item0}.png" width="45px"> <img src="/lol/img/item/${match.info.participants[status.index].item1}.png" width="45px"> <img src="/lol/img/item/${match.info.participants[status.index].item2}.png" width="45px"> <img src="/lol/img/item/${match.info.participants[status.index].item3}.png" width="45px"> <img src="/lol/img/item/${match.info.participants[status.index].item4}.png" width="45px"> <img
														src="/lol/img/item/${match.info.participants[status.index].item5}.png" width="45px"> <img src="/lol/img/item/${match.info.participants[status.index].item6}.png" width="45px">
												</div>
											</div>
										</div>
									</td>
									<td>${match.info.participants[status.index].kills} / ${match.info.participants[status.index].deaths} / ${match.info.participants[status.index].assists}</td>
									<td>${match.info.participants[status.index].totalDamageDealtToChampions}</td>
									<td>${match.info.participants[status.index].totalDamageTaken}</td>
									<td>${match.info.participants[status.index].totalMinionsKilled}</td>
									<td>${match.info.participants[status.index].goldEarned}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<%@ include file="/view/includes/09_footer.jsp"%>
	</div>
	
</body>
</html>
