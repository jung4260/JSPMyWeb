<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>
<!DOCTYPE html>
<html>
<head>


</head>
<body>

	<div align="center" class="div_center">
	<form action="" method="post" >
		<h3>회원정보 수정 페이지</h3>
		<hr>
		<table border="1">
			<tr>
				<td>아이디</td>
				<td>
					<input type="text" name="id" value="" readonly="readonly">
				</td>
			</tr>
			<tr>
				<td>비밀번호</td>
					<td>
						<input type="password" name="pw" placeholder="비밀번호는 5자 이상 입력">
					</td>
				</tr>
				<tr>
					<td>비밀번호 확인</td>
					<td>
						<input type="password" name="pw_check" placeholder="비밀번호는 5자 이상 입력">
					</td>
			</tr>
			<tr>
				<td>이름</td>
				<td>
					<input type="text" name="name" value="">
				</td>
				
			</tr>

			<tr>
				<td>이메일</td>
				<td>
					<input type="text" name="email" value="" placeholder="ex) abc@def.com">
				</td>
			</tr>
			<tr>
				<td>주소</td>
				<td>
					<input type="text" name="address" value="" placeholder="ex) OO시 OO구">
				</td>
			</tr>
		</table>
		<br>
		
		<input type="button" value="수정" class="btn btn-default" onclick="">&nbsp;&nbsp;
		<input type="button" value="취소" class="btn btn-default" onclick="">    
		
	</form>	
	</div>



	<%@ include file= "../include/footer.jsp" %>
	
</body>
</html>