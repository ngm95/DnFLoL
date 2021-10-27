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
		
		<div style="margin-right:10px; padding-right:10px; margin-bottom:10px; padding-bottom:10px">
			<form action="/lol/board/newPostGET" method="GET">
				<button type="submit" style="float:right; background-color:turquoise; color:white">새로운 글 작성</button>
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
			

			<div class="row">
				<c:choose>
					<c:when test="${bmm.prev eq 'true'}">
						<form action="/lol/board/prev" method="GET">
							<button type="submit" style="float:left; background-color:pink; color:white">이전 글</button>
						</form>
					</c:when>
					<c:otherwise>
						<form action="/lol/board/prev" method="GET">
							<button type="submit" style="float:left; background-color:pink; color:white" disabled>이전 글</button>
						</form>
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${bmm.next eq 'true'}">
						<form action="/lol/board/next" method="GET">
							<button type="submit" style="float:right; background-color:pink; color:white">다음 글</button>
						</form>
					</c:when>
					<c:otherwise>
						<form action="/lol/board/next" method="GET">
							<button type="submit" style="float:right; background-color:pink; color:white" disabled>다음 글</button>
						</form>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		
		

		<div class="jumbotron">
			<form:form modelAttribute="searchForm" action="/lol/findBoard" method="post">
				<div class="form-group has-feedback">
					<label for="findDetail">검색하기</label>
					<form:input type="text" class="form-control" placeholder="검색할 내용" path="findDetail" id="findDetail" />
					<span class="glyphicon glyphicon-user form-control-feedback"></span>
				</div>
				<div>

					<input type="radio" name="checkRadio" value="groupName" checked> 
					<label for="groupName" style="padding-right:10px">그룹 이름</label> 
					
					<input type="radio" name="checkRadio" value="groupOwner"> 
					<label for="groupOwner" style="padding-right:10px">작성자</label> 
					
					<input type="radio" name="checkRadio" value="detail"> 
					<label for="detail">세부 내용</label>
				</div>
				<div class="row">
					<div class="col-xs-8">
						<button type="submit" class="btn btn-style" style="background-color: blue; color: white">검색</button>
					</div>
				</div>
			</form:form>
		</div>
	</div>
	<%@ include file="/view/includes/09_footer.jsp"%>
</body>
</html>