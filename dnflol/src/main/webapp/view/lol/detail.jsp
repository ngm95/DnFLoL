<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/view/includes/00_head.jsp"%>
<title>게시글 세부내용</title>
</head>
<body class="board-pages">

	<div class="container contents-wrap" style="height: 100%">
		<%@ include file="/view/includes/03_header.jsp"%>
		<div class="jumbotron">
			<h4>${lgroupDto.lgroupName} created by ${lgroupDto.lgroupOwner}, ${lgroupDto.lgroupType}</h4>
			<h3>인원 수 : ${fn:length(acceptedList)+1} / ${lgroupDto.lgroupMax}</h3>
			
		</div>
		<%@ include file="/view/includes/09_footer.jsp"%>
	</div>
</body>
</html>
