<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div class="modal fade" id="postModal" role="dialog">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title">새로운 글 작성</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
			</div>
			<c:choose>
				<c:when test="${empty mylolChars}">
					<div class="modal-body">
						<p>연동된 LOL 계정이 없어서 게시글 작성이 불가능합니다.</p>
						<p>마이페이지에서 계정을 연동해주세요.</p>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary" data-bs-dismiss="modal" style="float:right; margin-left : 10px">닫기</button>
					</div>
				</c:when>
				<c:otherwise>
					<div class="modal-body">
						<form:form modelAttribute="post" action="/lol/board/newPost" method="post">
							<table class="table">
								<tr>
									<td>제목</td>
									<td><form:input class="form-control" type="text" path="lgroupName" placeholder="제목" /></td>
								</tr>
								<tr>
									<td>내 계정</td>
									<td><form:select class="form-select" path="lgroupOwner">
											<c:forEach var="chars" items="${mylolChars}">
												<form:option value="${chars.lcharName}">${chars.lcharName}</form:option>
											</c:forEach>
										</form:select></td>
								</tr>
								<tr>
									<td>게임 타입</td>
									<td><form:select class="form-select" path="lgroupType">
											<form:option value="듀오랭크">듀오랭크</form:option>
											<form:option value="자유랭크">자유랭크</form:option>
											<form:option value="일반게임">일반게임</form:option>
										</form:select></td>
								</tr>
								<tr>
									<td>설명</td>
									<td><form:textarea class="form-control" type="text" rows="5" path="lgroupDetail" placeholder="게시글 설명" /></td>
								</tr>
							</table>
							<button type="button" class="btn btn-secondary" data-bs-dismiss="modal" style="float:right; margin-left : 10px">닫기</button>
							<button id="modalSubmit" type="submit" class="btn btn-success" style="float:right">제출</button>
						</form:form>
					</div>
					<div class="modal-footer">
						
					</div>
				</c:otherwise>
			</c:choose>
			
		</div>
	</div>
</div>