<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>corona</title>
<style type="text/css">
#gra{
	height: 500px;
	width: 100%;
}
#item{
	height: 30px; 
	width: 100%;
	margin-bottom: 5px;
	background-color: gray;
}
#graG{
	height: 30px; 
	width: 100px;
	float: left;
}
#graItem{
	height: 30px;
	width: 30px;
	float: left;
	background-color: red;
}
</style>
</head>
<body>
	<h1>corona</h1>
	<div id="gra">
		<c:forEach items="${list }" var="g">
		<div id="item">
			<div id="graG">${g.stateDt}</div>
			<div id="graItem" style="width:<fmt:parseNumber value="${g.decideCnt / 1000}" integerOnly="true"/>px">
			<fmt:parseNumber value="${g.decideCnt / 1000}" integerOnly="true"/>
			</div>
		</div>
		</c:forEach>
	</div>
	
	
	
	<table border="1">
		<tr>
			<th>기준일</th>
			<th>확진자 수</th>
			<th>격리해제 수</th>
			<th>검사진행 수</th>
			<th>사망자수</th>
			<th>치료중 환자 수</th>
			<th>누적확진률</th>
		</tr>
		<c:forEach items="${list }" var="l">
		<tr>
			<td><fmt:parseDate var="dateString" value="${l.stateDt }" pattern="yyyyMMdd" /><fmt:formatDate value="${dateString}" pattern="yyyy.MM.dd" /></td>
			<td><fmt:formatNumber value="${l.decideCnt }" type="number"/></td>
			<td>${l.clearCnt }</td>
			<td>${l.examCnt }</td>
			<td>${l.deathCnt }</td>
			<td>${l.careCnt }</td>
			<td>
				<fmt:formatNumber value="${l.accDefRate }" pattern=".00"/>
				%
			</td>
		</tr>
		</c:forEach>
	</table>
	
</body>
</html>