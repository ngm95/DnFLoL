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
		<c:if test="${not empty error}">
			<jsp:include page="/view/includes/errorModal.jsp"></jsp:include>
		</c:if>
		
		<div class="jumbotron">
			<h3>계정 상세정보</h3>
			<div class="jumbotron-board">
				<pre style="font-size:3.5rem">${dcharDto.dcname}</pre>
				<img src="https://img-api.neople.co.kr/df/servers/${dcharDto.dcserver}/characters/${dcharDto.dcharId}?zoom=1" class="card-img-top">
			</div>
				
			<h3 style="margin-top : 25px"> 추가 정보 제목 </h3>
			<div class="jumbotron-board">
				
                    <table class="table table-striped">
					<thead>
						<tr>
							<th scope="col">레이드</th>
							<th scope="col">공대명</th>
							<th scope="col">시간</th>
						</tr>
					</thead>
					<tbody >
					<c:forEach var="el" items="${result}" varStatus="status">
						<tr>
							<td>${el.data.raidName}</td>
							<td>${el.data.raidPartyName}</td>
							<td>${el.date}</td>
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
