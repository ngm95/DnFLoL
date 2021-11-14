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
		<c:if test="${not empty error}">
			<jsp:include page="/view/includes/errorModal.jsp"></jsp:include>
		</c:if>
		
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
						<h4>수락된 신청</h4>
						<div class="row row-cols-6">
							<c:forEach var="accepted" items="${acceptedList}">
								<div class="col-6 col-md-4">
									<div class="card">
										<div class="card-body">
											<button class="btn btn-info" onclick="location.href='/lol/charDetail/${accepted.lcharName}'">${accepted.lcharName}</button>
											<c:if test="${authInfo.uid eq ownerUid and accepted.lcharName ne lgroupDto.lgroupOwner}">
												<form action="/lol/denyApply" method="post" style="float:right">
													<input name="${_csrf.parameterName}" type="hidden" value="${_csrf.token}" /> 
													<input type="hidden" name="lapplyId" value="${accepted.lapplyId}"> 
													<input type="hidden" name="lgroupId" value="${accepted.lgroupId}"> 
													<button type="submit" class="btn btn-danger" style="float:right">취소하기</button>
												</form>
											</c:if>
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
