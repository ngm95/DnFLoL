<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/view/includes/00_head.jsp"%>
<%@ include file="/view/includes/03_header.jsp"%>
<title>글 작성하기</title>
</head>
<body class="board-pages">
	
	<div class="jumbotron">
		<div class="jumbotron">
			<%@ page language="java" contentType="text/html; charset=UTF-8"
				pageEncoding="UTF-8"%>

			<div class="board-pages">
				<div class="jumbotron">
					<form:form modelAttribute="post" action="/lol/board/newPostPost"
						method="post">

						<div class="form-group has-feedback">
							<form:input type="text" class="form-control" placeholder="제목" path="lgroupName" id="lgroupName" />
							<span class="glyphicon glyphicon-user form-control-feedback"></span>
						</div>

						<div>
							<input type="radio" name="lgroupType" value="듀오랭크">듀오랭크
							<input type="radio" name="lgroupType" value="자유랭크">자유랭크
							<input type="radio" name="lgroupType" value="일반게임">일반게임
						</div>

						<div class="form-group has-feedback">
							<form:input type="text" class="form-control" placeholder="내용" path="lgroupDetail" id="lgroupDetail" />
							<span class="glyphicon glyphicon-lock form-control-feedback"></span>
						</div>

						<div class="row">
							<div class="col-xs-8"></div>
							<div class="col-xs-3">
								<button type="submit" class="btn btn-style">작성하기</button>
							</div>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
				