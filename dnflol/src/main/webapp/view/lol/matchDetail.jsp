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
		<jsp:include page="/view/includes/noticeModal.jsp"></jsp:include>
		<div class="jumbotron">
			<div class="jumbotron-board">
				<h3>게임 시간 : ${gameDuration}</h3>
				
				<div class="card" style="margin-top:30px">
					<br/>
					<div class="d-flex justify-content-between">
						<div class="d-flex">
							<h3 class="card-title" style="color:blue">&nbsp;<b>블루 팀</b></h3>
						</div>
						<div class="d-flex">
							<h3 class="card-title">K/D/A : 
								<b>${match.info.participants[0].kills+match.info.participants[1].kills+match.info.participants[2].kills+match.info.participants[3].kills+match.info.participants[4].kills}</b> / 
								<b>${match.info.participants[0].deaths+match.info.participants[1].deaths+match.info.participants[2].deaths+match.info.participants[3].deaths+match.info.participants[4].deaths}</b> /
								<b>${match.info.participants[0].assists+match.info.participants[1].assists+match.info.participants[2].assists+match.info.participants[3].assists+match.info.participants[4].assists}</b>
							</h3>
						</div>
						<div class="d-flex">
							<h3 class="card-title">Total Gold : 
								<b>${match.info.participants[0].goldEarned+match.info.participants[1].goldEarned+match.info.participants[2].goldEarned+match.info.participants[3].goldEarned+match.info.participants[4].goldEarned}</b>
							</h3>
						</div>
						<div class="d-flex">
							<c:choose>
								<c:when test="${match.info.participants[0].win eq true}">
									<h2 class="card-title" style="color: blue">
										<b>승리</b>&nbsp;
									</h2>
								</c:when>
								<c:otherwise>
									<h2 class="card-title" style="color: red">
										<b>패배</b>&nbsp;
									</h2>
								</c:otherwise>
							</c:choose>
						</div>
					</div>
					
					<div class="card-body">
						<table class="table table-striped">
							<thead>
								<tr>
									<th scope="col">계정명</th>
									<th scope="col">챔피언</th>
									<th scope="col">룬</th>
									<th scope="col">아이템</th>
									<th scope="col">K/D/A</th>
									<th scope="col">준 데미지</th>
									<th scope="col">받은 데미지</th>
									<th scope="col">CS</th>
									<th scope="col">Gold</th>
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
											</div>
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
											</div>
										</td>
										<td>
											<div class="d-flex flex-row">
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
										<td>${match.info.participants[status.index].totalMinionsKilled + match.info.participants[status.index].neutralMinionsKilled}</td>
										<td>${match.info.participants[status.index].goldEarned}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>

				<div class="card" style="margin-top : 15px">
					<br />
					<div class="d-flex justify-content-between">
						<div class="d-flex flex-column">
							<h3 class="card-title" style="color:red">&nbsp;<b>레드 팀</b></h3>
						</div>
						<div class="d-flex-column">
							<h3 class="card-title">K/D/A : 
								<b>${match.info.participants[5].kills+match.info.participants[6].kills+match.info.participants[7].kills+match.info.participants[8].kills+match.info.participants[9].kills}</b> / 
								<b>${match.info.participants[5].deaths+match.info.participants[6].deaths+match.info.participants[7].deaths+match.info.participants[8].deaths+match.info.participants[9].deaths}</b> /
								<b>${match.info.participants[5].assists+match.info.participants[6].assists+match.info.participants[7].assists+match.info.participants[8].assists+match.info.participants[9].assists}</b>
							</h3>
						</div>
						<div class="d-flex">
							<h3 class="card-title">Total Gold : 
								<b>${match.info.participants[5].goldEarned+match.info.participants[6].goldEarned+match.info.participants[7].goldEarned+match.info.participants[8].goldEarned+match.info.participants[9].goldEarned}</b>
							</h3>
						</div>
						<div class="d-flex flex-column">
							<c:choose>
								<c:when test="${match.info.participants[5].win eq true}">
									<h2 class="card-title" style="color: blue">
										<b>승리</b>&nbsp;
									</h2>
								</c:when>
								<c:otherwise>
									<h2 class="card-title" style="color: red">
										<b>패배</b>&nbsp;
									</h2>
								</c:otherwise>
							</c:choose>
						</div>
					</div>

					<div class="card-body">
						<table class="table table-striped">
							<thead>
								<tr>
									<th scope="col">계정명</th>
									<th scope="col">챔피언</th>
									<th scope="col">룬</th>
									<th scope="col">아이템</th>
									<th scope="col">K/D/A</th>
									<th scope="col">준 데미지</th>
									<th scope="col">받은 데미지</th>
									<th scope="col">CS</th>
									<th scope="col">Gold</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach begin="5" end="9" step="1" varStatus="status">
									<tr>
										<td>${match.info.participants[status.index].summonerName}</td>
										<td>
											<div class="d-flex flex-row">
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
											</div>
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
											</div>
										</td>
										<td>
											<div class="d-flex flex-row">
												<div class="d-flex flex-column" style="margin-left: 5px; margin-top: 5px">
													<div class="d-flex flex-row">
														<img src="/lol/img/item/${match.info.participants[status.index].item0}.png" width="45px"> <img src="/lol/img/item/${match.info.participants[status.index].item1}.png" width="45px"> <img src="/lol/img/item/${match.info.participants[status.index].item2}.png" width="45px"> <img src="/lol/img/item/${match.info.participants[status.index].item3}.png" width="45px"> <img src="/lol/img/item/${match.info.participants[status.index].item4}.png" width="45px"> <img
															src="/lol/img/item/${match.info.participants[status.index].item5}.png" width="45px"> <img src="/lol/img/item/${match.info.participants[status.index].item6}.png" width="45px">
													</div>
												</div>
											</div>
										</td>
										<td>${match.info.participants[status.index].kills}/ ${match.info.participants[status.index].deaths} / ${match.info.participants[status.index].assists}</td>
										<td>${match.info.participants[status.index].totalDamageDealtToChampions}</td>
										<td>${match.info.participants[status.index].totalDamageTaken}</td>
										<td>${match.info.participants[status.index].totalMinionsKilled + match.info.participants[status.index].neutralMinionsKilled	}</td>
										<td>${match.info.participants[status.index].goldEarned}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
		<%@ include file="/view/includes/09_footer.jsp"%>
	</div>
	
</body>
</html>
