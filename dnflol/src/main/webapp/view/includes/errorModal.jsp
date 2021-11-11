<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="modal fade" id="errorModal" role="dialog">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title">오류 발생</h5>
				<button type="button" class="btn-close" data-dismiss="modal"></button>
			</div>
			<div id="modalBody" class="modal-body">
				<c:if test="${not empty error}">
					${error.message}
				</c:if>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary" data-bs-dismiss="modal" style="float:right; margin-left : 10px">닫기</button>
			</div>
		</div>
	</div>
</div>