<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="https://unpkg.com/bootstrap@3.3.7/dist/css/bootstrap.min.css">
<title>회원가입</title>
</head>
<body>
<div align="center" style="margin:auto auto">
		<h1>회원가입</h1>
		<hr>		
		<form action="JoinPro" method="post" name="joinForm">
			<table class="table" style="width:500px">
				<tr>
					<td>아이디</td>
					<td>
					<input type="text" name="id" id="id" class="id">	
					<font id = "checkId"></font>
					<input type="checkbox" name="idchk" value="unchk"  disabled="disabled" id="idchk" >			
					</td>
				</tr>
				<tr>
					<td>비밀번호</td>
					<td><input type="password" name="password"></td>
				</tr>
				<tr>
					<td>비밀번호 확인</td>
					<td><input type="password" name="passwordchk"></td>
					<font id = "checkpw"></font>			
				</tr>
				<tr>
					<td>이름</td>
					<td><input type="text" name="name"></td>
				</tr>
				<tr>
					<td colspan="2" align="center">					
					<input type="button" value="회원가입"  class="btn btn-primary" onclick="checkJoin();">
					<input type="reset" value="취소"  class="btn btn-primary">
					</td>					
				</tr>
			</table>
		</form>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
		<script type="text/javascript">
			function checkJoin() {
				var form =  document.joinForm;
				
				if(form.id.value==""||form.id.value==null){
					alert("아이디를 입력하세요");
					return false;
				}
				if(form.idchk.value=="unchk"){
					alert("아이디 중복을 확인하세요");
					return false;
				}
				if(form.password.value==""||form.password.value==null){
					alert("비밀번호를 입력하세요");
					return false;
				}
				if(form.password.value!=form.passwordchk.value){
					alert("입력하신 비밀번호와 같은 비밀번호를 입력하세요");
					return false;
				}
				if(form.name.value==""||form.name.value==null){
					alert("이름을 입력하세요");
					return false;
				}
				
				form.submit();				
			}		
			
			$('.id').focusout(function(){
				let userId = $('.id').val();
				
					$.ajax({
						url : "Idchkajax",
						type : "post",
						data : {userId: userId},
						dataType : 'json',
						success : function(result){
							if(result == 0){
								$("#checkId").html('사용할 수 없는 아이디입니다.');
								$("#checkId").attr('color','red');
								$("#idchk").val('unchk');
							}else{
								$("#checkId").html('사용할 수 있는 아이디입니다.');
								$("#checkId").attr('color','blue');
								$("#idchk").val('chked');
							}
						},
						error : function(){
							alert("서버요청실패");
						}
						
					})
					
			})
			
		</script>
	</div>
</body>
</html>