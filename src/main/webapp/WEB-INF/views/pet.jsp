<%@page import="com.michael.po.Pet"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
<h2>Pets</h2>
<c:forEach items="${pets }" var="p" varStatus="stat">
	<c:out value="${stat.count }"></c:out>
	<c:out value="${p.masterId }"></c:out>
	<c:out value="${p.id }"></c:out>
	<c:out value="${p.petName }"></c:out>
	<c:out value="${p.age }"></c:out>
	<c:out value="${p.gender }"></c:out>
	<c:out value="${p.species }"></c:out><br/>
	<c:out value="======"></c:out><br/>
</c:forEach>
</body>
</html>