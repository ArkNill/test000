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
src="${pageContext.request.contextPath}/resource/js/index/login.js"></script>
</head>

<body>
	<div class="text-center">
		<h1 class="h4 text-gray-900 mb-4">Welcome To SDTM</h1>
	</div>
	<form id="loginForm" action="${pageContext.request.contextPath}/loginMember" method="post">
		<div class="form-group">
			<input type="text" class="form-control form-control-user"
				id="login_member_id" name="member_id" placeholder="CICD TEST">
		</div>
		
		<div class="form-group">
			<input type="password" class="form-control form-control-user"
				id="login_pwd" name="pwd" placeholder="CICD Password">
		</div>

		<div class="form-group">
			<div class="custom-control custom-checkbox small">
				<input type="checkbox" class="custom-control-input" id="customCheck"> 
				<label class="custom-control-label" for="customCheck">Auto Login</label>
			</div>
		</div>
		
		<div class="form-group">
			<input type="submit" value="Login" class="form-control form-control-user">
		</div>
	</form>
</body>
</html>
