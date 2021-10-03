<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${dto.sb_no }번 글수정하기</title>
<link href="./css/index.css" rel="stylesheet">
<style type="text/css">
#update{
	margin: 0 auto;
	height: 600px;
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
<link href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">
  <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
  <script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
  <link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.css" rel="stylesheet">
  <script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.js"></script>

</head>
<body>
<div id="container">
		<div id="header">
			<c:import url="/menu" />
		</div>
		<div id="main">
	<div id="update">
	<form action="update" method="post">
		<input type="text" name="sb_title" value="${dto.sb_title }">
		${dto.sb_date }에 글 씀.
		<textarea name="sb_content" id="summernote">${dto.sb_content }</textarea>
		<input type="hidden" name="sb_no" value="${dto.sb_no }">
		<button>글수정</button>
	</form>
	</div>
	</div>
	</div>
	<script type="text/javascript">
$(document).ready(function() {
	  $('#summernote').summernote({
		  height:400
	  });
	});
</script>
</body>
</html>