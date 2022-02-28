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
			<table class="table" style="width:420px">
				<tr>
					<td>아이디</td>
					<td>
					<input type="text" name="id" id="id">
					<input type="button" value="중복확인" onclick="openIdChk();" class="btn btn-primary" id="btn">
					<!--중복체크를 했는지 확인하는 체크박스. 안보이게 하기전에 확인하려고 놔둠.  -->
					<input type="checkbox" name="idchk" value="unchk"  disabled="disabled" >
					</td>
				</tr>
				<tr>
					<td>비밀번호</td>
					<td><input type="password" name="password"></td>
				</tr>
				<tr>
					<td>비밀번호 확인</td>
					<td><input type="password" name="passwordchk"></td>
				</tr>
				<tr>
					<td>이름</td>
					<td><input type="text" name="name"></td><!-- 회원가입 양식 더 많이 추가하기! -->
				</tr>
				<tr>
					<td colspan="2" align="center">					
					<input type="button" value="회원가입"  class="btn btn-primary" onclick="checkJoin();">
					<input type="reset" value="취소"  class="btn btn-primary">
					</td>					
				</tr>
			</table>
		</form>
		<script type="text/javascript">
		
			//가입 폼 체크
			function checkJoin() {
				var form =  document.joinForm;
				
				if(form.id.value==""||form.id.value==null){
					alert("아이디를 입력하세요");
					return false;
				}
				if(form.idchk.value=="unchk"){
					alert("아이디 중복확인을 해주세요");
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
			
			
			var idchkPopup = null;
			
			//팝업창 나오게 하기.
			function openIdChk(){				
				window.name = "parentForm";
				idchkPopup=window.open("idchkForm.jsp","idchkForm","top=30, left=800, width=490, height=220, status=no, menubar=no, toolbar=no");		
			}
			
			//*****팝업을 열때 같이 값을 전달시키는 방법. 이걸쓰면 자식창에 js코드를 길게 쓸 필요가 없음. !!!!!!*****
			/* var p;
			function popIdCheckForm(){
				var data=document.all.id.value;
				p=window.open("idCheckForm.jsp?id="+data,"popup","left=10,top=10,width=600,height=600,status=no,scrollbars=no,titlebar=no");
				//팝업창을 get방식으로 id값을 받아서 전송.. 자식창이 열려있는 상태에서 값은 전달이 되지만 열리는 동시에 window.open 바로 아래에 값을 전송하는 코드가
				//수행되지 않는다.
			} */
			
						
		</script>
	</div>
</body>
</html>