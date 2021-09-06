<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>category</title>
</head>
<body>
	<c:forEach items="${category }" var="c">
		${c.sc_no } / ${c.sc_category } / ${c.sc_date } 
		<a href="./categoryUpdate?sc_no=${c.sc_no }">[수정]</a><br>
	</c:forEach>
	
	//추가하는 폼
	<form action="./category" method="post">
		<input type="text" name="categoryName">
		<button type="submit">등록</button>
	</form>
	<hr>
	
</body>
</html>