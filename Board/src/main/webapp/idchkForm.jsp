<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<% 
String filePath = request.getParameter("filePath");
String id = (String)request.getAttribute("id");
%>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="https://unpkg.com/bootstrap@3.3.7/dist/css/bootstrap.min.css">
<title>중복 아이디 확인창</title>
</head>
<body>
<div align="left" style="margin:0 auto 0 20px">
<header>
<h3 align="left">아이디 중복확인</h3>
</header>
<p>아이디는 영문(소문자), 숫자로 4~16자 이내로 입력해 주세요.</p><!--나중에 아이디 생성조건도 검사하는 식 만들기!  -->
<form action="Idchk" method="post">
<input type="text" name="id" size="20" id="id">	
<input type ="submit" name="idchk" value="중복확인" class="btn btn-primary">
<%
if(filePath==null || filePath.equals("-1")){
%>
<%}else{ %>
<input type="button" value="전송" onclick="submitToParent(); return false;" class="btn btn-primary">
<%} %>
</form>	

<% //검색에 따른 결과를 filePath를 통해 다르게 보여준다. 가능할 아이디일시 전송가능 버튼이 나오게함.
if (filePath==null) {	
%>
<p></p>
<%
}else if (filePath.equals("-1")||filePath=="") {	
%>
<p>사용할 수 없는 아이디 입니다.</p>
<%
} else if (filePath.equals("1")) {
%>
<p>사용가능한 아이디 입니다. 전송버튼을 눌러주세요.</p>
<%} %>
<footer>
<p style="color:red; font-size: small;">*공백 또는 특수문자가 포함된 아이디는 사용할 수 없습니다.</p>
<p style="color:red; font-size: small;">*숫자로 시작하거나, 숫자로만 이루어진 아이디는 사용할 수 없습니다.</p>
</footer>
</div>
<script type="text/javascript">


	function submitToParent() {  //사용할 수 있는 아이디 값 부모창으로 전달하기	
		opener.document.all.id.value = document.all.id.value;
		opener.document.all.idchk.value="chked"
		opener.document.all.idchk.checked=true;
		self.close();		
	}
	
	
	//팝업 로드시 부모창 값을 받아오고 그 이 후로는 서블릿 검색 값을 넘겨 받는다.
	<%
	if(id==null||id==""){ 
	%>	
	var parentid = opener.document.getElementById("id").value;
	document.getElementById("id").value=parentid;
	<%}else{%>	
	document.getElementById("id").value="<%=id%>";
	<%}%>
	
	
	
</script>
</body>
</html>