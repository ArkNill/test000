$(function() {
	var idCheck = false; //id 중복검사 여부
	var idOk = false; //id 사용가능 여부
	
	/* Context Path */
	var contextPath = getContextPath();
	
	/* id 중복검사 */
	$('#idCheck').click(function() {
		$.ajax({
			url : "idCheck",
			type : "POST",
			data : { "member_id" : $('#register_member_id').val() },
			success : function(data) {
				if (data == 0 && $.trim($('#register_member_id').val()) != '') {
					idCheck = true;
					idOk = true;
					let html = "<div style='color: green'>사용가능</div>";
					$('#isExistId').empty();
					$('#isExistId').append(html);
				} else {
					idCheck = true;
					idOk = false;
					let html = "<div style='color: red'>사용불가능한 아이디입니다.</div>";
					$('#isExistId').empty();
					$('#isExistId').append(html);
				}
			},
			error : function(request, status, error) {
				alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error + "서버에러");
			}
		}); //ajax
	}); //click
	
	/* id를 새로 입력하면 중복검사를 안 한 것으로 표기 */
	$('#register_member_id').keyup(function(){
		idCheck = false;
		let html = "<div style='color: blue'>아이디 중복체크가 필요합니다.</div>";
		$('#isExistId').empty();
		$('#isExistId').append(html);
		if($(this).val() == ''){
			html = "<div style='color: blue'>아이디를 입력하세요.</div>";
			$('#isExistId').empty();
			$('#isExistId').append(html);
		}
	});
	
	/* 비밀번호 입력 검사 */
	$('#register_pwd').keyup(function(){
		let pwd = $('#register_pwd').val();
		if(pwd == ''){
			let html = "<div style='color: blue'>비밀번호를 입력하세요</div>";
			$('#isSamePwd').empty();
			$('#isSamePwd').append(html);
		}
	});
	
	/* 비밀번호 일치 검사 */
	$('#register_pwd_check').keyup(function(){
		let pwd = $('#register_pwd').val();
		let pwdCheck = $('#register_pwd_check').val();
		if(pwd != ''){
			if(pwd != pwdCheck){
				let html = "<div style='color: red'>비밀번호가 일치하지 않습니다.</div>";
				$('#isSamePwd').empty();
				$('#isSamePwd').append(html);
			}else{
				let html = "<div style='color: green'>비밀번호 일치</div>";
				$('#isSamePwd').empty();
				$('#isSamePwd').append(html);
			}
		}
	});
	
	/* 회원가입 제출 */
	$('#register_submit').click(function(){
		let member_id = $('#register_member_id').val();
		let pwd = $('#register_pwd').val();
		let pwdCheck = $('#register_pwd_check').val();
		if(member_id == '' || pwd == '' || pwdCheck == ''){
			alert("빈칸을 입력하세요.");
			return false;
		}else if(idCheck === false){
			alert("아이디 중복체크가 필요합니다.");
			return false;
		}else if(idOk === false){
			alert("사용불가능한 아이디입니다.");
			return false;
		}else if(pwd != pwdCheck){
			alert("비밀번호가 일치하지 않습니다.");
			return false;
		}
		$.ajax({
			url : "registerMember",
			type : "POST",
			data : $('#registerForm').serialize(),
			contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
			async: false,
			success : function(data) {
				alert(data);
				location.href = contextPath + "/index.jsp";
			},
			error : function(request, status, error) {
				alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error + "서버에러");
			}
		});
	}); //click
}); //ready

function getContextPath() {
    var hostIndex = location.href.indexOf( location.host ) + location.host.length;
    return location.href.substring( hostIndex, location.href.indexOf('/', hostIndex + 1) );
}
