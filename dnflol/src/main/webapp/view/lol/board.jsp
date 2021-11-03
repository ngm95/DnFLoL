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
			<h3>게시판</h3>
			<div class="jumbotron-board" style="margin-top:45px">
				<c:choose>
					<c:when test="${empty lgroupList}">
						<h3>작성된 글이 없습니다!</h3>
					</c:when>
				</c:choose>

				<table class="table table-striped">
					<thead>
						<tr>
							<th scope="col">글 번호</th>
							<th scope="col">제목</th>
							<th scope="col">게임 종류</th>
							<th scope="col">작성자</th>
							<th scope="col">시간</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="lgroup" items="${lgroupList}">
							<tr>
								<th scope="row">${lgroup.lgroupId}</th>
								<td><a href="/lol/boardDetail/${lgroup.lgroupId}">${lgroup.lgroupName}</a></td>
								<td>${lgroup.lgroupType}</td>
								<td>${lgroup.lgroupOwner}</td>
								<td>${lgroup.lgroupDate}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
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
				
				<c:forEach begin="0" end="9" step="1" varStatus="status">
					<c:choose>
						<c:when test="${bmm.limit - (bmm.paging+status.index)*10  <= -10}">
							<li class="page-item disabled"><a class="page-link" href="/lol/board/${bmm.paging + status.index}">${bmm.paging + status.index}</a></li>
						</c:when>
						<c:otherwise>
							<li class="page-item"><a class="page-link" href="/lol/board/${bmm.paging + status.index}">${bmm.paging + status.index}</a></li>
						</c:otherwise>
					</c:choose>
				</c:forEach>
				
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
						<form:option value="groupName">제목</form:option>
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