<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">

<title>Register</title>

<!-- Custom fonts for this template-->
<link
	href="${pageContext.request.contextPath}/resource/vendor/fontawesome-free/css/all.min.css"
	rel="stylesheet" type="text/css">
<link
	href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
	rel="stylesheet">

<!-- Custom styles for this template-->
<link
	href="${pageContext.request.contextPath}/resource/css/sb-admin-2.min.css"
	rel="stylesheet">
<script
	src="${pageContext.request.contextPath}/resource/js/jquery-3.5.1.min.js"></script>
<script 
	src="${pageContext.request.contextPath}/resource/js/index/register.js"></script>
<script>
</script>	
</head>

<body>
	<div class="text-center">
		<h1 class="h4 text-gray-900 mb-4">Create an Account!</h1>
	</div>
	<form method="post" id="registerForm" name="register">
		<div class="form-group">
			<input style="display:inline-block; width:63%;" type="text" class="form-control" name="member_id"
				id="register_member_id" placeholder="Member ID" required> 
			<input style="display:inline-block; width:35%;" type="button" class="form-control" id="idCheck" value="중복체크">
			<div style="margin-top:2px;" id="isExistId"></div>
		</div>
		
		<div class="form-group">
			<input type="password" class="form-control" name="pwd"
				id="register_pwd" placeholder="Password">
			<input type="password" class="form-control" name="pwdCheck"
				id="register_pwd_check"  placeholder="Password Check">
			<div style="margin-top:2px;" id="isSamePwd"></div>
		</div>
		
		<div class="form-group">
			<input type="button" id="register_submit" value="Register Account"
				class="form-control form-control-user">
		</div>
	</form>
</body>
</html>
