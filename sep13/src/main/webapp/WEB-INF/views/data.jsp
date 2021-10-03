<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>data.go.kr에서 뽑아온 값입니다.</title>
</head>
<body>
	<h1>data</h1>
	
	<table border="1">
		<tr>
			<th>측정일</th>
			<th>지역명</th>
			<th>아화산 가스</th>
			<th>일산화탄소</th>
			<th>오존</th>
			<th>이산화질소</th>
			<th>PM10 미세먼지</th>
			<th>PM10 미세먼지</th>
		</tr>
		<c:forEach items="${list }" var="l">
		<tr>
			<td>${l.msurDt }</td>
			<td>${l.msrstnName }</td>
			<td>${l.so2Value }</td>
			<td>${l.coValue }</td>
			<td>${l.o3Value }</td>
			<td>${l.no2Value }</td>
			<td>${l.pm10Value }</td>
			<td>${l.pm25Value }</td>
		</tr>
		</c:forEach>
	</table>
	
</body>
</html>