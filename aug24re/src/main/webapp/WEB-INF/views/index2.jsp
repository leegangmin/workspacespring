<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Hello World</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<style type="text/css">
/* reset.css */
*{margin:0;padding:0;list-style:none;}
a{color:#333;text-decoration:none;transition:0.3s all;}
a:visited, a:focus{color:#333;}

/* wrapper */
body{background-color:eee;}
#wrapper{width:100%;margin:0 auto;}

/* header.css */
header{width:100%;text-align:center;border-bottom:3px solid; border-image: linear-gradient(to right, #fbfcb9, #ffcdf3, #65d3ff);border-image-slice: 1;}
	h1{line-height:100px;background-color:#F4EBD9;}
		h1 span{display:block;width:900px;margin:0 auto;text-align:left;}
	nav{width:900px;margin:0 auto;overflow:hidden;padding-bottom:10px;}
		nav ul{width:100%;overflow:hidden;}
			nav ul li{float:left;height:40px;line-height:40px;text-align:center;}
				.navHover{display:block;margin:3px 8px;height:36px;border-bottom:2px solid rgba(192,192,192,0);font-size:17px;font-weight:600;transition:0.3s all;}
				.navHover:hover{border-bottom:2px solid #784212;}
		nav ul:after{content:""; display:none;clear:both;}
		
/* main.css */
#container{width:900px; margin:0 auto;padding-top:20px;}
	.title{overflow:hidden;text-align:center;border-bottom:1px solid #ccc;height:35px;line-height:35px;}
		.title li{width:15%; height:100%; float:left;}
		.title li:nth-child(2){width:35%;text-align:left;}
	
	#subTitle{margin-bottom:5px;font-weight:600;background-color:#F7F7F7;border-top:2px solid #444;color:#444;}
	#main{transition:0.3s all;cursor:pointer;}
	#main:hover{background-color:#EBF5FB;}
</style>
<script>
	
</script>
</head>
<body>
<div id="wrapper">
	<header>
		<h1><span>${category }</span></h1>
		<nav>
			<ul>
				<c:forEach items="${categoryList }" var="cl">
					<li>
						<a class="navHover" href="./board?sb_cate=${cl.sc_no }">${cl.sc_category }</a>		
					</li>
				</c:forEach>
			</ul>
		</nav>
	</header>
	<main id="container">
		<ul id="subTitle" class="title">
			<li>번호</li>
			<li>제목</li>
			<li>날짜</li>
			<li>조회수</li>
			<li>글쓴이</li>
		</ul>
		<c:choose>
			<c:when test="${fn:length(list) gt 0 }">
				<c:forEach items="${list }" var="l">
					<ul id="main" class="title" onclick="location.href='./detail?sb_no=${l.sb_no }'">
						<li>${l.sb_no }</li>
						<li>${l.sb_title }</li>
						<li>${l.sb_date }</li>
						<li>${l.sb_count }</li>
						<li>${l.sm_name }<small>(${l.sm_id })</small></li>
					</ul>
				</c:forEach>
			</c:when>
			<c:otherwise>
				출력할 글이 없습니다.<br/><br/>
			</c:otherwise>
		</c:choose>
	</main>
	<footer></footer>
</div>
</body>
</html>