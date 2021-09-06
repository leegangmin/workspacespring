<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글쓰기</title>
<link href="./css/index.css" rel="stylesheet">
<style type="text/css">
#update{
	margin: 0 auto;
	height: 300px;
	width: 600px;	
}
input{
	width: 100%;
	height: 30px;
}
textarea{
	width: 100%;
	height: 300px
}
</style>
</head>
<body>
<div id="container">
		<div id="header">
			<c:import url="/menu" />
		</div>
		<div id="main">
	<div id="update">
	<form action="write" method="post">
		<input type="text" name="sb_title">
		<textarea name="sb_content"></textarea>
		<button>글쓰기</button>
		<input type="hidden" name="sb_cate" value="${param.sb_cate}">
	</form>
	</div>
	</div>
	</div>
</body>
</html>