<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div class="modal fade" id="findModal" role="dialog">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title">새로운 글 작성</h5>
				<button type="button" class="btn-close" data-dismiss="modal"></button>
			</div>
			<div class="modal-body">
				<form:form modelAttribute="summoner" action="/lol/findSummoner" method="post">
					<table class="table">
						<tr>
							<td>아이디</td>
							<td><form:input type="text" class="form-control" path="name" placeholder="아이디"/></td>
						</tr>
					</table>
					<button type="button" class="btn btn-secondary" data-bs-dismiss="modal" style="float: right; margin-left: 10px">닫기</button>
					<button id="modalSubmit" type="submit" class="btn btn-success" style="float: right">검색</button>
				</form:form>
				
				<div class="card">
					<div class="card-body">
						<c:choose>
							<c:when test="${'IRON' eq leagueDto.tier}">
								<img src="/lol/img/ranked-emblems/Emblem_Iron.png" style="width: 5%">
							</c:when>
							<c:when test="${'BRONZE' eq leagueDto.tier}">
								<img src="/lol/img/ranked-emblems/Emblem_Bronze.png" style="width: 5%">
							</c:when>
							<c:when test="${'SILVER' eq leagueDto.tier}">
								<img src="/lol/img/ranked-emblems/Emblem_Silver.png" style="width: 5%">
							</c:when>
							<c:when test="${'GOLD' eq leagueDto.tier}">
								<img src="/lol/img/ranked-emblems/Emblem_Gold.png" style="width: 5%">
							</c:when>
							<c:when test="${'PLATINUM' eq leagueDto.tier}">
								<img src="/lol/img/ranked-emblems/Emblem_Platinum.png" style="width: 5%">
							</c:when>
							<c:when test="${'DIAMOND' eq leagueDto.tier}">
								<img src="/lol/img/ranked-emblems/Emblem_Diamond.png" style="width: 5%">
							</c:when>
							<c:when test="${'MASTER' eq leagueDto.tier}">
								<img src="/lol/img/ranked-emblems/Emblem_Master.png" style="width: 5%">
							</c:when>
							<c:when test="${'GRANDMASTER' eq leagueDto.tier}">
								<img src="/lol/img/ranked-emblems/Emblem_Grandmaster.png" style="width: 5%">
							</c:when>
							<c:when test="${'CHALLENGER' eq leagueDto.tier}">
								<img src="/lol/img/ranked-emblems/Emblem_Challenger.png" style="width: 5%">
							</c:when>
						</c:choose>
						아이디 : ${summoner.name}, 레벨 : ${summoner.summonerLevel}, 티어 : ${leagueDto.tier} ${leagueDto.rank}
						<form:form modelAttribute="summoner" action="/lol/addSummoner" method="post">
							<div class="col-xs-3">
								<form:input type="hidden" path="name" value="${summoner.name}" />
								<button type="submit" class="btn btn-success" style="float: right">계정과 연동하기</button>
							</div>
						</form:form>
					</div>
				</div>
			</div>
			<div class="modal-footer"></div>

		</div>
	</div>
</div>