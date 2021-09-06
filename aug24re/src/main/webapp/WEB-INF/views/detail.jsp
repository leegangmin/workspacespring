<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>detail : ${dto.sb_no }</title>
<link href="./css/index.css" rel="stylesheet">
<style type="text/css">
#detail{
	margin: 0 auto;
	width: 800px;
	background-color: gray;
	height: auto;
	padding: 5px;
}
</style>
</head>
<body>
	<div id="container">
		<div id="header">
			<c:import url="/menu" />
		</div>
		<div id="main">
			<div id="detail">
				글 제목 : ${dto.sb_title }
				<!-- 자신의 글에서만 삭제하기/수정하기가 보여야 합니다 -->
				<c:if test="${dto.sm_id eq sessionScope.sm_id }">
					<button onclick="location.href='./delete?sb_no=${dto.sb_no }'">
					삭제하기</button>
					<button onclick="location.href='./update?sb_no=${dto.sb_no }'">
					수정하기</button>
				</c:if>
				
				<hr>
				<br>${dto.sm_name }(<small>${dto.sm_id }</small>)
				<br>날짜 : ${dto.sb_date } | 조회수 : ${dto.sb_count }
				<hr>
				${dto.sb_content }
				<hr>
			<a href="./board">보드로 이동</a>
			<!-- 여기 수정했습니다. -->
			<a href="./write?sb_cate=${dto.sb_cate }">글쓰기</a>
			</div>
		</div>
	</div>
</body>
</html>