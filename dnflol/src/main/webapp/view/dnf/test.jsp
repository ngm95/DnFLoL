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
        <c:forEach var="el" items="${result}">
                    <p> ${el.code}, ${el.date}</p>
        
        </c:forEach>
        </div>
    </div>

</body>
</html>