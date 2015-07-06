<%@ page language="java" import="java.util.*" pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>

<form action="<%=request.getContextPath() %>/showPets" method="post">
	<input type="text" name="masterId">
	<input type="text" name="remark">
	<input type="submit" name="showPets" value="查询宠物列表" />
</form>
<hr/>

<form action="<%=request.getContextPath() %>/writer" method="post">
	<input type="text" name="id">
	<input type="text" name="remark">
	<input type="submit" name="writer" value="查询指定宠物" />
</form>
<hr/>

<form action="<%=request.getContextPath() %>/edit" method="post">
	<input type="text" name="id">
	<input type="submit" name="edit" value="编辑指定宠物" />
</form>
</body>
</html>
