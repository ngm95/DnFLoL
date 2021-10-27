<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/view/includes/00_head.jsp"%>
<title>계정 상세정보</title>
</head>
<body class="board-pages">

	<div class="container" style="height: 100%">
		<%@ include file="/view/includes/03_header.jsp"%>
		
		<div class="jumbotron">
			<h4>${lcharDto.lcharName}</h4>
			<p>티어 : ${leagueDto.tier} ${leagueDto.rank}, ${leaguePoints}p</p>
		</div>
		
	</div>
	<%@ include file="/view/includes/09_footer.jsp"%>
</body>
</html>
