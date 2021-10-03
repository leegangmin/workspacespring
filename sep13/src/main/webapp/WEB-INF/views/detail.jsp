<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>detail</title>
<script type="text/javascript">
function move(){
	location.href='./main.do';
}
function del(no){
	location.href="./delete.do?sb_no="+no;
}
</script>
</head>
<body>
	값 찍기
	<hr>
	글번호 : ${detail.sb_no } <button onclick="del(${detail.sb_no })">삭제</button><br>
	글제목 : ${detail.sb_title }<br>
	글쓴이 : ${detail.sm_name }(${detail.sm_id })<br>
	쓴 날짜 : ${detail.sb_date }<br>
	내용 : ${detail.sb_content }<br>
	
	<button onclick="move()">게시판으로</button>
</body>
</html>