<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String id =(String) request.getAttribute("id");
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>idchkFalse</title>
</head>
<body>
<h3> <%=id %>는 사용할 수있습니다. </h3>

<a>해당 아이디 사용</a> &nbsp;&nbsp;


</body>
</html>