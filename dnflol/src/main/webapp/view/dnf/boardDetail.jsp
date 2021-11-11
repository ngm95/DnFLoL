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
			<button type="button" class="btn btn-success" onclick="location.href='/dnf/board'" style="float:right">목록</button>
			<h3><b>게시글 상세</b></h3>
			<div class="jumbotron-board" style="margin-top:45px">
				<c:if test="${authInfo.uid eq ownerUid}">
					<button type="button" class="btn btn-warning" onclick="location.href='/dnf/board/delete/${dgroupDto.dgroupId}'" style="float:right">글 삭제</button>
				</c:if>
				<table class="table">
					<tr>
						<td>제목</td>
						<td><input class="form-control" type="text" value="${dgroupDto.dgroupName}" readonly/></td>
					</tr>
					<tr>
						<td>내 계정</td>
						<td><select class="form-select" disabled>
								<option value="${dgroupDto.dgroupOwnerName}">${dgroupDto.dgroupOwnerName}</option>
						</select></td>
					</tr>
					<tr>
						<td>게임 타입</td>
						<td><select class="form-select" disabled>
								<c:choose>
									<c:when test="${dgroupDto.dgroupType == 1}">
										<option value="핀드워">핀드워</option>
									</c:when>
									<c:when test="${dgroupDto.dgroupType == 2}">
										<option value="프레이-이시스">프레이-이시스</option>
									</c:when>
									<c:when test="${dgroupDto.dgroupType == 3}">
										<option value="무형의 시로코">무형의 시로코</option>
									</c:when>
									<c:when test="${dgroupDto.dgroupType == 4}">
										<option value="혼돈의 오즈마">혼돈의 오즈마</option>
									</c:when>
								</c:choose>
						</select></td>
					</tr>
					<tr>
						<td>설명</td>
						<td><textarea class="form-control" readonly>${dgroupDto.dgroupDetail}</textarea></td>
					</tr>
				</table>
			</div>

			<h3>참가 인원 수 : ${fn:length(acceptedList)} / ${dgroupDto.dgroupMax}</h3>
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
									<button class="btn btn-info" onclick="location.href='/dnf/charDetail/${accepted.dcname}'">${accepted.dcname}</button>
										<c:if test="${authInfo.uid eq ownerUid and accepted.dcname ne dgroupDto.dgroupOwnerName}">
											<button class="btn btn-danger" onclick="location.href='/dnf/denyApply/${accepted.dapplyId}&${accepted.dgroupId}'">수락 거절</button>
										</c:if>
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
								<form:form modelAttribute="applyForm" style="display: inline-block" action="${pageContext.request.contextPath}/dnf/submit/" method="post">
									<p>계정명 : ${chars.dcname} 
									<input type="hidden" id="dcharId" name="dcharId" value="${chars.dcharId}"> 
									<input type="hidden" id="dcname" name="dcname" value="${chars.dcname}"> 
									<input type="hidden" id="dgroupId" name="dgroupId" value="${dgroupDto.dgroupId}"> 
									<input type="hidden" id="dgroupName" name="dgroupName" value="${dgroupDto.dgroupName}">
									<input type="submit" value="신청하기">
								</form:form>
							</div>
						</c:forEach>
						<c:forEach var="chars" items="${myAppliedChars}">
							<div>
								<form:form modelAttribute="applyForm" style="display: inline-block" action="${pageContext.request.contextPath}/dnf/submit/" method="post">
									<p>계정명 : ${chars.dcname} 
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
