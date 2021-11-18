<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/view/includes/00_head.jsp"%>
<title>LoL 게시글 세부내용</title>
</head>
<body>
	<div class="container contents-wrap" style="height: 100%">
		<%@ include file="/view/includes/03_header.jsp"%>
		<jsp:include page="/view/includes/noticeModal.jsp"></jsp:include>
		
		<div class="jumbotron">
			<button type="button" class="btn btn-success" onclick="location.href='/lol/board'" style="float:right">목록</button>
			<h3><b>게시글 상세</b></h3>
			<div class="jumbotron-board" style="margin-top:45px">
				<c:if test="${authInfo.uid eq ownerUid}">
					<form action="/lol/board/delete" method="post">
						<input name="${_csrf.parameterName}" type="hidden" value="${_csrf.token}" />
						<input name="ownerUid" type="hidden" value="${ownerUid}" />
						<input name="lgroupId" type="hidden" value="${lgroupDto.lgroupId}" />
						<button type="submit" class="btn btn-warning" style="float: right">글 삭제</button>
					</form>
				</c:if>
				<table class="table">
					<tr>
						<td>제목</td>
						<td><input class="form-control" type="text" value="${lgroupDto.lgroupName}" readonly/></td>
					</tr>
					<tr>
						<td>작성자</td>
						<td><select class="form-select" disabled>
								<option value="${lgroupDto.lgroupOwner}">${lgroupDto.lgroupOwner}</option>
						</select></td>
					</tr>
					<tr>
						<td>게임 타입</td>
						<td><select class="form-select" disabled>
								<option value="${lgroupDto.lgroupType}">${lgroupDto.lgroupType}</option>
						</select></td>
					</tr>
					<tr>
						<td>설명</td>
						<td><textarea class="form-control" readonly>${lgroupDto.lgroupDetail}</textarea></td>
					</tr>
				</table>
			</div>
			
			<h3>참가 인원 수 : ${fn:length(acceptedList)} / ${lgroupDto.lgroupMax}</h3>
			<div class="jumbotron-board" style="margin-top:45px">
				<c:choose>
					<c:when test="${empty acceptedList}">
						<p>아직 수락된 신청이 없습니다.</p>
					</c:when>
					<c:otherwise>
						<div style="display:inline-block; padding-bottom:15px">
							<h4 style="float:left">수락된 신청</h4>
							<button id="spinner" class="btn btn-info" type="button" style="display:none; margin-left:15px;" disabled>
								<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span> 
								로딩중...
							</button>
						</div>
						<div class="row row-cols-6">
							<c:forEach begin="0" end="${fn:length(acceptedList)-1}" step="1" varStatus="status">
								<div class="col-6 col-md-4">
									<div class="card" style="margin-bottom:20px">
										<div class="card-body">
											<div class="d-flex">
												<div class="d-flex flex-row">
													<div class="d-flex flex-column">
														<img src="/lol/img/profileicon/${summonerList[status.index].profileIconId}.png" width="50px">
													</div>
													<div class="d-flex flex-column" style="margin-left:10px; margin-top:7px">
														<form action="/lol/charDetail" target="_blank" method="post">
															<input name="${_csrf.parameterName}" type="hidden" value="${_csrf.token}" /> 
															<input type="hidden" name="matches" value="${matches[status.index]}" />
															<input type="hidden" name="lcharName" value="${acceptedList[status.index].lcharName}" />
															<button type="submit" class="btn btn-info">${acceptedList[status.index].lcharName}</button>
														</form>
													</div>
													<div class="d-flex flex-column" style="margin-left:30px; margin-top:7px">
														<c:if test="${authInfo.uid eq ownerUid and acceptedList[status.index].lcharName ne lgroupDto.lgroupOwner}">
															<form action="/lol/denyApply" method="post">
																<input name="${_csrf.parameterName}" type="hidden" value="${_csrf.token}" /> 
																<input type="hidden" name="lapplyId" value="${acceptedList[status.index].lapplyId}"> 
																<input type="hidden" name="lgroupId" value="${acceptedList[status.index].lgroupId}"> 
																<button type="submit" class="btn btn-danger">취소하기</button>
															</form>
														</c:if>	
													</div>
												</div>
											</div>
											<div style="display:block">
												
											</div>
										</div>
									</div>
								</div>
							</c:forEach>
						</div>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		
		<div class="jumbotron">
			<h3>내 LOL 계정</h3>
			<div class="jumbotron-board">
				<c:choose>
					<c:when test="${empty myAppliedChars and empty myNotAppliedChars}">
						<p>연동된 LOL 계정이 없습니다.</p>
						<a href="/user/myPage">연동하러 가기</a>
					</c:when>
					<c:otherwise>
						<c:forEach var="chars" items="${myNotAppliedChars}">
							<div>
								<form:form modelAttribute="applyForm" style="display: inline-block" action="/lol/submit/" method="post">
									<p>계정명 : ${chars.lcharName} 
									<input type="hidden" id="lgroupId" name="lgroupId" value="${lgroupDto.lgroupId}"> 
									<input type="hidden" id="lcharName" name="lcharName" value="${chars.lcharName}"> 
									<input type="hidden" id="lgroupName" name="lgroupName" value="${lgroupDto.lgroupName}">
									<input type="submit" value="신청하기">
								</form:form>
							</div>
						</c:forEach>
						<c:forEach var="chars" items="${myAppliedChars}">
							<div>
								<form:form modelAttribute="applyForm" style="display: inline-block" action="$/lol/submit/" method="post">
									<p>계정명 : ${chars.lcharName} 
									<input type="submit" value="신청하기" disabled>
								</form:form>
							</div>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<%@ include file="/view/includes/09_footer.jsp"%>
	</div>
</body>
</html>
