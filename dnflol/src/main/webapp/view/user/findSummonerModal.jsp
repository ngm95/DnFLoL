<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<div class="modal fade" id="findSummonerModal" role="dialog">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title">LoL 계정 검색</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
			</div>
			<div class="modal-body">
				<table class="table">
					<tr>
						<td>아이디</td>
						<td><input id="summonerName" type="text" class="form-control" placeholder="아이디"/></td>
						<td><button id="findBtn" type="button" class="btn btn-primary">검색</button></td>
					</tr>
				</table>
				
				
				<div class="card">
					<div class="card-body">
						<h3>검색 결과</h3>
						<hr/>
						<div id="resultBody" class="jumbotron-board" style="padding-top : 10px; padding-bottom : 10px;">
							<h4>검색하지 않았거나 검색 결과가 없습니다.</h4>
							
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				
				
				<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
			</div>
		</div>
	</div>
</div>