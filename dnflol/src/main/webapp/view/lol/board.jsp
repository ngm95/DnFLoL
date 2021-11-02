<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/view/includes/00_head.jsp"%>
<title>LOL 게시판</title>
</head>
<body class="board-pages">

	<div class="container contents-wrap" style="height: 100%">
		<%@ include file="/view/includes/03_header.jsp"%>
		<div class="jumbotron">
		
			<button type="button" class="btn btn-info" onclick="location.href='/lol/board/newPostGET'" style="float:right">새로운 글 작성</button>
			<div class="jumbotron-board" style="margin-top:45px">
				<c:choose>
					<c:when test="${empty lgroupList}">
						<h3>작성된 글이 없습니다!</h3>
					</c:when>
				</c:choose>
				<c:forEach var="lgroup" items="${lgroupList}">
					<div class="groupboard" style="font-family: 'Nanum Gothic'; font-size: 1.6em" id="${lgroup.lgroupId}">
						<a href="/lol/boardDetail/${lgroup.lgroupId}">${lgroup.lgroupName} created by ${lgroup.lgroupOwner}, ${lgroup.lgroupType}        ${lgroup.lgroupDate}</a>
					</div>
				</c:forEach>
			</div>
			

			<nav>
			<ul class="pagination justify-content-center">
				<c:choose>
					<c:when test="${bmm.prev eq 'true'}">
						<li class="page-item"><a class="page-link" href="/lol/board/prev">이전</a></li>
					</c:when>
					<c:otherwise>
						<li class="page-item disabled"><a class="page-link">이전</a></li>
					</c:otherwise>
				</c:choose>
				<li class="page-item"><a class="page-link" href="/lol/board/1">${bmm.paging}</a></li>
				<li class="page-item"><a class="page-link" href="/lol/board/2">${bmm.paging + 1}</a></li>
				<li class="page-item"><a class="page-link" href="/lol/board/3">${bmm.paging + 2}</a></li>
				<li class="page-item"><a class="page-link" href="/lol/board/4">${bmm.paging + 3}</a></li>
				<li class="page-item"><a class="page-link" href="/lol/board/5">${bmm.paging + 4}</a></li>
				<li class="page-item"><a class="page-link" href="/lol/board/6">${bmm.paging + 5}</a></li>
				<li class="page-item"><a class="page-link" href="/lol/board/7">${bmm.paging + 6}</a></li>
				<li class="page-item"><a class="page-link" href="/lol/board/8">${bmm.paging + 7}</a></li>
				<li class="page-item"><a class="page-link" href="/lol/board/9">${bmm.paging + 8}</a></li>
				<li class="page-item"><a class="page-link" href="/lol/board/10">${bmm.paging + 9}</a></li>
				<c:choose>
					<c:when test="${bmm.next eq 'true'}">
						<li class="page-item"><a class="page-link" href="/lol/board/next">다음</a></li>
					</c:when>
					<c:otherwise>
						<li class="page-item disabled"><a class="page-link">다음</a></li>
					</c:otherwise>
				</c:choose>
			</ul>
			</nav>
		</div>

		<div class="jumbotron">
			<form:form modelAttribute="searchForm" action="/lol/findBoard" method="post">
				<div class="input-group">
					<form:select class="form-select" path="checkRadio">
						<form:option value="groupName">그룹 이름</form:option>
						<form:option value="groupOwner">작성자</form:option>
						<form:option value="detail">세부 내용</form:option>
					</form:select>
					<form:input class="form-control" type="text" path="findDetail" placeholder="검색할 내용"/>
					<form:button class="btn btn-outline-secondary" type="submit">검색하기</form:button>
				</div>
			</form:form>
		</div>
		<%@ include file="/view/includes/09_footer.jsp"%>
	</div>
	
</body>
</html>