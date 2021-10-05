<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/view/includes/00_head.jsp" %>
    <%@ include file="/view/includes/03_header.jsp" %>
<title>Access Deined</title>
</head>
<body>
      <br><br>
      <div class="container text-center">
          <h1>접근 거부됨</h1><br>
      </div>
      <br><br>
      <div class="container text-center">
          <h6 class="font-italic text-danger">권한이 없어 접근이 불가합니다.<br>관리자에게 문의하세요.</h6>
      </div>
      <br><br>
      <sec:authorize access="isAnonymous()">
      	<p>아직 로그인하지 않으셨나요?</p> <a href="/security/login">로그인하러 가기</a>
      </sec:authorize>
      <div class="container text-center">
        <a href='<c:url value="/"/>' class="text-dark"><i class="fas fa-undo"></i></a>
      </div>
</body>
</html>