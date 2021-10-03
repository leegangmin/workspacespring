<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>main</title>
<script type="text/javascript">
function linkPage(pageNo){
	//location.href="./main.do?pageNo="+pageNo;
	ajax1(pageNo);
}
</script>
<script src="https://code.jquery.com/jquery-3.6.0.js" integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>
<script type="text/javascript">
$(document).ready(
		function(){
			ajax1(1);
		});
		
function ajax1(pageNo){
	$.ajax({
		url:"./mainAJAX.do",
		type:"POST",
		cache:false,
		dataType:"json",
		data:{"pageNo":pageNo},
		success: function(data){
			//alert("정상 : pageNo : " + data.pageNo);
			//alert("정상 : sb_cate : " + data.sb_cate);
			//alert("정상 : list : " + data.list);
			emp(data);
		},
		error: function(requst, status, error){
			alert("error : " + error);
		}
	});
}
		
function emp(data){
	$("#mainTable").empty(); //안에 비우기
	$("#paging").empty();//모두 지우고 다시 만들기
	
	var pageNo = data.pageNo;
	var list = data.list;
	var totalCount = data.totalCount;
	var temp = "<h1>페이지 번호 : " + pageNo + "</h1>";
	
	temp += "<table>";
	temp += "<tr>";
	temp += 	"<td>번호</td><td>제목</td><td>글쓴이</td><td>날짜</td>";
	temp += "</tr>";
	
	for (var i=0; i<list.length; i++){
		temp+="<tr>";
		temp+=    "<td>" + list[i].sb_no + "</td>";
		temp+=    "<td>" + list[i].sb_title + "</td>";
		temp+=    "<td>" + list[i].sm_id + "</td>";
		temp+=    "<td>" + list[i].sb_date + "</td>";
		temp+="</tr>";
	}
	
	temp += "</table>";
	
	$("#mainTable").append(temp);
	
	//페이징 찍기 // 길고 어려움 주의
	//pageNo, totalCount
	var totalPage = parseInt(totalCount / 10); 
	if(totalCount % 10 != 0){
		totalPage += 1; //10으로 나눈 나머지의 값이 있을 떄 +1
	}
	var startPage = parseInt(pageNo / 10) * 10 + 1 ; // 혹시나 여긴 나중에 수정
	
	if(pageNo % 10 == 0 ){
		startPage = pageNo -9; 
	} 
	
	var endPage = startPage + 9; //여기도 나중에 수정 
	if(totalPage < endPage){
		endPage = totalPage;
	}
	
	// 출력
	var paging = "";
	
	// 첫 페이지 설정
	if(pageNo != 1){
		paging += "<button onclick=ajax1(1)>처음</button>";	
	}else{
		paging +="<button disabled='disabled')>처음</button>";	
	}
	
	// 이전 페이지 설정
	if(pageNo > 1){
		paging += "<button onclick=ajax1("+ (pageNo-1) +")>이전</button>";	
	}else{
		paging +="<button disabled='disabled')>이전</button>";	
	}
	
	//for출력
	for(var i = startPage; i <= endPage; i++){
		if(pageNo != i){
			paging += "<button onclick=ajax1("+i+")>" + i + "</button>";			
		}else{
			paging += "<button onclick=ajax1("+i+")><b>" + i + "</b></button>";						
		}
	}
	
	// 이후 페이지 설정
	if(pageNo < totalPage){
		paging += "<button onclick=ajax1(" + (pageNo+1) + ")>이후</button>"		
	}else{
		paging += "<button disabled='disabled')>이후</button>"			
	}
	
	if( pageNo < totalPage){
		paging += "<button onclick=ajax1(" + (totalPage) + ")>마지막</button>"		
	}else{
		paging += "<button disabled='disabled')>마지막</button>"			
	}
	
	
	$("#paging").append(paging);
	
}

</script>
</head>
<body>
	<h1>main이 실행됩니다.</h1>
	<c:choose>
		<c:when test="${sessionScope.sm_name ne null}">
			<h2>${sessionScope.sm_name }님 반갑습니다.
			<a href="./message.do">쪽지</a></h2>
		</c:when>
		<c:otherwise>
			<a href="./login.do">로그인 해주세요.</a>
		</c:otherwise>
	</c:choose>
	<div id="mainTable">
		여기다가 찍어줄 겁니다
	</div>
	<!-- 페이징 찍어줄 곳 -->
	<div id="paging">
		<%-- <ui:pagination paginationInfo="${paginationInfo }" type="text" jsFunction="linkPage"/> --%>
	</div>
</body>
</html>