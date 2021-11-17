<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/view/includes/00_head.jsp"%>
<title>DnF 캐릭터 상세정보</title>
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
				<pre style="font-size: 2.0rem">${dcharDto.dcname} </pre>
				<div class="row-vh d-flex flex-row justify-content-start"> <!--flex-row일 때는 안써도 된다.-->

           			<div class="row-vh d-flex flex-column ">

            				<div class="row-vh d-flex flex-row ">

            				<c:choose>
								<c:when test="${Equipresult[3].upgradeInfo.itemId ne null}">
            						<img src=	 "https://img-api.neople.co.kr/df/items/${Equipresult[3].upgradeInfo.itemId}">
								</c:when>
					
								<c:otherwise>
									<img src=	 "https://img-api.neople.co.kr/df/items/${Equipresult[3].itemId}">
								</c:otherwise>
							</c:choose>

            				<c:choose>
								<c:when test="${Equipresult[2].upgradeInfo.itemId ne null}">
            						<img src=	 "https://img-api.neople.co.kr/df/items/${Equipresult[2].upgradeInfo.itemId}">
								</c:when>
					
								<c:otherwise>
									<img src=	 "https://img-api.neople.co.kr/df/items/${Equipresult[2].itemId}">
								</c:otherwise>
							</c:choose>

							</div>

            				<div class="row-vh d-flex flex-row ">

            				<c:choose>
								<c:when test="${Equipresult[6].upgradeInfo.itemId ne null}">
            						<img src=	 "https://img-api.neople.co.kr/df/items/${Equipresult[6].upgradeInfo.itemId}">
								</c:when>
					
								<c:otherwise>
									<img src=	 "https://img-api.neople.co.kr/df/items/${Equipresult[6].itemId}">
								</c:otherwise>
							</c:choose>

            				<c:choose>
								<c:when test="${Equipresult[4].upgradeInfo.itemId ne null}">
            						<img src=	 "https://img-api.neople.co.kr/df/items/${Equipresult[4].upgradeInfo.itemId}">
								</c:when>
					
								<c:otherwise>
									<img src=	 "https://img-api.neople.co.kr/df/items/${Equipresult[4].itemId}">
								</c:otherwise>
							</c:choose>

							</div>
							
							<div class="row-vh d-flex flex-row ">

            				<c:choose>
								<c:when test="${Equipresult[5].upgradeInfo.itemId ne null}">
            						<img src=	 "https://img-api.neople.co.kr/df/items/${Equipresult[5].upgradeInfo.itemId}">
								</c:when>
					
								<c:otherwise>
									<img src=	 "https://img-api.neople.co.kr/df/items/${Equipresult[5].itemId}">
								</c:otherwise>
							</c:choose>


							</div>

							</div>

            		<div  class="item align-self-center"><img src="https://img-api.neople.co.kr/df/servers/${dcharDto.dcserver}/characters/${dcharDto.dcharId}?zoom=1"></div>
					<div class="row-vh d-flex flex-column ">

            				<div class="row-vh d-flex flex-row ">

            				<img src=	 "https://img-api.neople.co.kr/df/items/${Equipresult[0].itemId}">

            				<img src=	 "https://img-api.neople.co.kr/df/items/${Equipresult[1].itemId}">

							</div>

            				<div class="row-vh d-flex flex-row ">

            				<c:choose>
								<c:when test="${Equipresult[8].upgradeInfo.itemId ne null}">
            						<img src=	 "https://img-api.neople.co.kr/df/items/${Equipresult[8].upgradeInfo.itemId}">
								</c:when>
					
								<c:otherwise>
									<img src=	 "https://img-api.neople.co.kr/df/items/${Equipresult[8].itemId}">
								</c:otherwise>
							</c:choose>

            				<c:choose>
								<c:when test="${Equipresult[7].upgradeInfo.itemId ne null}">
            						<img src=	 "https://img-api.neople.co.kr/df/items/${Equipresult[7].upgradeInfo.itemId}">
								</c:when>
					
								<c:otherwise>
									<img src=	 "https://img-api.neople.co.kr/df/items/${Equipresult[7].itemId}">
								</c:otherwise>
							</c:choose>

							</div>
							
							<div class="row-vh d-flex flex-row ">

            				<c:choose>
								<c:when test="${Equipresult[8].upgradeInfo.itemId ne null}">
            						<img src=	 "https://img-api.neople.co.kr/df/items/${Equipresult[10].upgradeInfo.itemId}">
								</c:when>
					
								<c:otherwise>
									<img src=	 "https://img-api.neople.co.kr/df/items/${Equipresult[10].itemId}">
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test="${Equipresult[9].upgradeInfo.itemId ne null}">
            						<img src=	 "https://img-api.neople.co.kr/df/items/${Equipresult[9].upgradeInfo.itemId}">
								</c:when>
					
								<c:otherwise>
									<img src=	 "https://img-api.neople.co.kr/df/items/${Equipresult[9].itemId}">
								</c:otherwise>
							</c:choose>
							</div>
							
							<div class="row-vh d-flex flex-row ">

            				<c:choose>
								<c:when test="${Equipresult[12].upgradeInfo.itemId ne null}">
            						<img src=	 "https://img-api.neople.co.kr/df/items/${Equipresult[12].upgradeInfo.itemId}">
								</c:when>
					
								<c:otherwise>
									<img src=	 "https://img-api.neople.co.kr/df/items/${Equipresult[12].itemId}">
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${Equipresult[11].upgradeInfo.itemId ne null}">
            						<img src=	 "https://img-api.neople.co.kr/df/items/${Equipresult[11].upgradeInfo.itemId}">
								</c:when>
					
								<c:otherwise>
									<img src=	 "https://img-api.neople.co.kr/df/items/${Equipresult[11].itemId}">
								</c:otherwise>
							</c:choose>
							</div>

							</div>
            		

				</div>
				
				<c:forEach var="eqel" items="${Equipresult}" >
				
								</c:forEach>
				<h2>한달간 레이드 드랍 횟수 ${fn:length(itemresult)}/${fn:length(result)}</h2>
				<c:forEach var="itemel" items="${itemresult}" >
									<img src=	 https://img-api.neople.co.kr/df/items/${itemel.data.itemId}>
								</c:forEach>
								
								
			</div>
				
			<h3 style="margin-top : 25px"> 추가 정보 제목 </h3>
			<div class="jumbotron-board">
				
                    <table class="table table-striped">
					<thead>
						<tr>
							<th scope="col">레이드</th>
							<th scope="col">공대명</th>
							<th scope="col">시간</th>
							<th scope="col">득템</th>
						</tr>
					</thead>
					<tbody  >
					<c:forEach var="el" items="${result}" varStatus="status">
					<c:if test="${el.data.phaseName ne '추적'}">
						<tr>
							<td>${el.data.raidName}</td>
							<td>${el.data.raidPartyName}</td>
							<td>${el.date}</td>
							<td>
								<c:forEach var="itemel" items="${itemresult}" >
									<c:if test="${el.date eq itemel.date}">
										${itemel.data.itemName}
									</c:if>
								</c:forEach>
							</td>
						</tr>
					</c:if>
					</c:forEach>
					</tbody>
				</table>
        
			</div>
		</div>
		<%@ include file="/view/includes/09_footer.jsp"%>
	</div>
	
</body>
</html>
