<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입하기</title>
<link href="./css/index.css" rel="stylesheet">
<style type="text/css">
#join {
	margin: 0 auto;
	height: auto;
	width: 500px;
	background-color: lime;
	padding: 10px;
	text-align: center;
}

table {
	margin: 0 auto;
	height: 100%;
	width: 100%;
	border-collapse: collapse;
	margin-bottom: 10px;
}

th, td {
	border-bottom: 1px solid silver;
}

td {
	text-align: left;
}

input {
	width: 180px;;
	height: 40px;
	margin: 5px;
	padding: 5px;
}

button {
	width: 45%;
	height: 40px;
	font-size: large;
}
</style>
<!-- jquery가져오기 -->
<script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.6.0.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		//alert("정상작동합니다.");
		//onchange를 여기에 연결하셔도 됩니다.
		$("#joinbtn").click(function(){
			//alert("가입버튼");
			var id = $("#id").val();
			var name = $("#name").val();
			var pw1 = $("#pw1").val();
			var pw2 = $("#pw2").val();
			var email = $("#email").val();
			if(id == "" || id.length < 5 ){
				alert("ID는 5글자 이상 입력해주세요");
				$("#id").focus();
				return false;
			}
			if(name == "" || name.length < 3 ){
				alert("이름은 3글자 이상 입력해주세요");
				$("#name").focus();
				return false;
			}
			if(pw1 != pw2){
				alert("암호가 일치하지 않습니다.");
				$("#pw1").val("");//내용 지우기
				$("#pw2").val("");//내용 지우기
				$("#pw1").focus();
				return false;
			}
			if(email == "" || email.length < 5 ){
				alert("email은 5글자 이상 입력해주세요");
				$("#email").focus();
				return false;
			}
		});
	});
	
	function check(){
		//alert('변경되었습니다.');
		//alert(    $("#id").val()     );
		var id = $("#id").val();
		if(id == "" || id.length < 5){
			//alert("아이디는 다섯 글자 이상이어야 합니다.");
			$("#checkResult").css("color","red");
			$("#checkResult").text("아이디는 다섯 글자 이상입니다.");
			$("#id").focus();
			$("#joinbtn").attr("disabled", true);
			return false;
		}else{
			//alert("가입가능합니다.");
			$.ajax({
				url : "./checkID",
				type : "POST",
				cache : false,
				dataType : "html",
				data : {'id' : id},
				success : function(data){
					//alert(data);
					if(data == 0){
						//alert("가입할 수 있습니다.");
						$("#checkResult").css("color","blue");
						$("#checkResult").text("가입 가능합니다.");
						$("#joinbtn").attr("disabled", false);
						
					}else{
						//alert("이미 가입되어 있습니다.");
						$("#checkResult").css("color", "red");
						$("#checkResult").text("이미 가입되어 있습니다. 다른 ID를 입력하세요.");
						$("#joinbtn").attr("disabled", true);
					}
				},
				error : function(request, status, error){
					alert(error);
				}
			});			
		}
		return false;
	}
</script>
</head>
<body>
	<div id="container">
		<div id="header">
			<%-- <jsp:include page="menu.jsp"/> --%>
			<c:import url="/menu" />
		</div>
		<div id="main">
			<div id="join">
				<h1>회원가입하기</h1>
				<form action="./join" method="post">
					<table>
						<tr>
							<th>아 이 디</th>
							<td><input type="text" name="id" id="id" required="required" onchange="return check()">
								<p id="checkResult">아이디를 입력하세요.</p>
							</td>
						</tr>
						<tr>
							<th>이 &emsp; 름</th>
							<td><input type="text" name="name" id="name" required="required"></td>
						</tr>
						<tr>
							<th>비밀번호</th>
							<td><input type="password" name="pw1" id="pw1" required="required">
								<input type="password" name="pw2" id="pw2" required="required"></td>
						</tr>
						<tr>
							<th>이 메 일</th>
							<td><input type="email" name="email" id="email" required="required"></td>
						</tr>
					</table>
					<button type="submit" id="joinbtn" disabled="disabled">가입하기</button>
					<button type="reset">입력취소</button>
				</form>
			</div>
		</div>
	</div>
</body>
</html>