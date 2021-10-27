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

	<div class="container" style="height: 100%" style="margin-bottom:0px; padding-bottom:0px">
		<%@ include file="/view/includes/03_header.jsp"%>
		
		<div>
			<form action="/lol/board/newPostGET" method="GET">
				<button class="btn btn-style" type="submit" style="float:right; background-color:turquoise; color:white">새로운 글 작성</button>
			</form>
		</div>
		
		<div class="jumbotron">
			<c:choose>
				<c:when test="${empty lgroupList}">
					<h3>작성된 글이 없습니다!</h3>
				</c:when>
				<c:otherwise>
					<c:forEach var="lgroup" items="${lgroupList}">
						<div class="alert alert-primary" role="alert" style="background-color:yellowgreen">
							<a href="/lol/boardDetail/${lgroup.lgroupId}">${lgroup.lgroupName} created by ${lgroup.lgroupOwner}, ${lgroup.lgroupType}</a>
						</div>
					</c:forEach>
				</c:otherwise>
			</c:choose>
			

			<div class="form-group">
				<c:choose>
					<c:when test="${bmm.prev eq 'true'}">
						<form action="/lol/board/prev" method="GET">
							<button class="btn btn-style" type="submit" style="float:left; background-color:pink; color:white">이전 글</button>
						</form>
					</c:when>
					<c:otherwise>
						<form action="/lol/board/prev" method="GET">
							<button class="btn btn-style" type="submit" style="float:left; background-color:pink; color:white" disabled>이전 글</button>
						</form>
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${bmm.next eq 'true'}">
						<form action="/lol/board/next" method="GET">
							<button class="btn btn-style" type="submit" style="float:right; background-color:pink; color:white">다음 글</button>
						</form>
					</c:when>
					<c:otherwise>
						<form action="/lol/board/next" method="GET">
							<button class="btn btn-style" type="submit" style="float:right; background-color:pink; color:white" disabled>다음 글</button>
						</form>
					</c:otherwise>
				</c:choose>
			</div>
		</div>

		<div class="jumbotron">
			<h3><b>검색하기</b></h3>
			<p>여기 아래쪽 다 일렬로 붙이고싶은데 마음대로 안됨</p>
			<form:form modelAttribute="searchForm" action="/lol/findBoard" method="post">
				<div class="form-row">
					<form:select path="checkRadio">
						<form:option value="groupName">제목</form:option>
						<form:option value="groupOwner">작성자</form:option>
						<form:option value="detail">설명</form:option>
					</form:select>
					
					<form:input type="text" class="form-control" placeholder="검색할 내용" path="findDetail"/>
					
					<form:button class="btn btn-style" type="submit" style="background-color: blue; color: white">검색하기</form:button>
				</div>
			</form:form>
		</div>
	</div>
	<%@ include file="/view/includes/09_footer.jsp"%>
</body>
</html>