<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@ include file="/view/includes/00_head.jsp" %>
    <title>LOL 게시판</title>
</head>
<body class="board-pages">

    <div class="container" style="height: 100%">
		<%@ include file="/view/includes/03_header.jsp"%>
        <div class="jumbotron">
            <c:forEach var="lgroup" items="${lgroupList}">
            	<div class="groupboard" id="${lgroup.lgroupId}">
            		<a href="/lol/board/detail/${lgroup.lgroupId}">${lgroup.lgroupName} created by ${lgroup.lgroupOwner}, ${lgroup.lgroupType}</a>
            	</div>
            </c:forEach>

			<c:choose>
				<c:when test="${bmm.prev eq 'true'}">
					<form action="/lol/board/prev" method="GET">
						<div>
							<button type="submit" class="btn btn-style">이전 글</button>
						</div>
					</form>
				</c:when>
				<c:otherwise>
					<form action="/lol/board/prev" method="GET">
						<div>
							<button type="submit" class="btn btn-style" disabled>이전 글</button>
						</div>
					</form>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${bmm.next eq 'true'}">
					<form action="/lol/board/next" method="GET">
						<div>
							<button type="submit" class="btn btn-style">다음 글</button>
						</div>
					</form>
				</c:when>
				<c:otherwise>
					<form action="/lol/board/next" method="GET">
						<div>
							<button type="submit" class="btn btn-style" disabled>다음 글</button>
						</div>
					</form>
				</c:otherwise>
			</c:choose>
			
			<form action="/lol/board/find" method="post">
				<div class="form-group has-feedback">
					<label for="findDetail">검색하기</label>
					<form:input type="text" class="form-control" placeholder="검색할 내용" path="findDetail" id="findDetail" />
					<span class="glyphicon glyphicon-user form-control-feedback"></span>
				</div>
				<div>
					
					<input type="radio" name="checkRadio" value="groupName" checked>
					<label for="groupName">그룹 이름</label>
					
					<input type="radio" name="checkRadio" value="groupOwner">
					<label for="groupOwner">작성자</label>
					
					<input type="radio" name="checkRadio" value="detail">
					<label for="detail">세부 내용</label>
				</div>
				<div class="row">
					<div class="col-xs-8">
						<button type="submit" class="btn btn-style" style="background-color:blue; color:white">검색</button>
					</div>
				</div>
			</form>

			<form action="/lol/board/newPostGET" method="GET">
                <div>
                    <button type="submit" class="btn btn-style">새로운 글 작성</button>
                </div>
            </form>
        </div>
    </div>

</body>
</html>