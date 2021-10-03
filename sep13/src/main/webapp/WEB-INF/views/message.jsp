<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>messages</title>
<style type="text/css">
body{
	margin: 0;
	padding: 0;
}
#message {
	
}

#box {
	float: left;
	width: 50%;
	height: 500px;
	background-color: lime;
}

#box table {
	height:100px;
	border-collapse: collapse;
}

td {
	border-bottom: 1px gray solid;
}

#view {
	float: left;
	width : 50%;
	height: 500px;
	background-color: aqua;
	width: 50%;
}

#send {
	width: 90%;
	background-color: gray
}

#send input {
	width: 100%;
	height: 30px;
}
</style>
<script type="text/javascript">
function delMsg(no){
	if(confirm("삭제하시겠습니까?")){
		alert("쪽지를 삭제합니다.");
		var form = document.createElement("form");
		form.setAttribute("action","./delMsg.do");
		form.setAttribute("method","post");
		var data = document.createElement("input");
		data.setAttribute("type","hidden");
		data.setAttribute("name","no");
		data.setAttribute("value",no);
		form.appendChild(data);
		document.body.appendChild(form);
		form.submit();
	return false;
	}
}
</script>
</head>
<body>
	<div id="message">
		<div id="box">
			<h2>메시지 리스트</h2>
			<table>
				<tr>
					<td>번호</td>
					<td>보낸사람</td>
					<td>날짜</td>
					<td>수신여부</td>
				</tr><c:forEach items="${list }" var="m">
				<tr onclick="location.href='./message.do?openmsg=${m.me_no }'"<c:if test="${m.me_read eq 0}">style="font-weight:bold"</c:if><c:if test="${m.me_read eq 1}">style="color:gray;"</c:if>>
					<td>${m.me_no }</td>
					<td>${m.sm_name }(${m.sm_id })</td>
					<td>${m.me_date }</td>
					<td><c:if test="${m.me_read eq 0}">안읽음</c:if><c:if test="${m.me_read eq 1}">읽음</c:if></td>
				</tr></c:forEach>
			</table>
			<div id="send">
				<form action="./message.do" method="post">
					<input type="text" name="sendID" value="${sendmsg }" placeholder="아이디를 입력하세요.">
					<input type="text" name="content" placeholder="내용을 입력하세요.">
					<button>메시지 보내기</button>
				</form>
			</div>
		</div>
		<div id="view"><c:choose><c:when test="${detail ne null }">
			번호 : ${detail. me_no}<br>
			보낸 사람 : ${detail.sm_name}(${detail.me_sendp})
			<span onclick="return delMsg(${detail. me_no})">[삭제]</span><br>
			보낸 id : ${detail.sm_id}<br>
			보낸 날짜 : ${detail.me_date}<br>
			보낸 내용 : ${detail.me_content}<br>
			<button onclick="location.href='./message.do?sendmsg=${detail.me_sendp}'">답장하기</button></c:when></c:choose>
		</div>

	</div>
</body>
</html>