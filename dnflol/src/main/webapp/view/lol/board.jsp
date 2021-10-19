<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@ include file="/view/includes/00_head.jsp" %>
    <%@ include file="/view/includes/03_header.jsp" %>
    <title>메인 페이지</title>
</head>
<body class="board-pages">

    <div class="jumbotron">
        <div class="jumbotron">
            <c:forEach var="lgroup" items="${lgroupList}">
            	<div class="groupboard" id="${lgroup.lgroupId}">
            		<a href="/lol/board/detail/${lgroup.lgroupId}">${lgroup.lgroupName} created by ${lgroup.lgroupOwner}, 
            			<c:choose>
            				<c:when test="${lgroup.lgrouptype eq '1'}">일반듀오</c:when>
            				<c:when test="${lgroup.lgrouptype eq '2'}">일반다인큐</c:when>
            				<c:when test="${lgroup.lgrouptype eq '3'}">랭크듀오</c:when>
            				<c:when test="${lgroup.lgrouptype eq '4'}">자유랭크</c:when>
            			</c:choose>
            		</a>
            	</div>
            </c:forEach>
        </div>
    </div>

</body>
</html>