	var loginResult = '';
	$(function() {
		/* 로그인 폼을 제출하기 전 아이디와 비밀번호가 일치하는지 검사함 */
		$('#loginForm').submit(function(){
			var member_id = $('#login_member_id').val();
			var pwd = $('#login_pwd').val();
			
			$.ajax({
				//요청
				type: "POST",
				url: "isMember", 
				data: $('#loginForm').serialize(),
				async: false,
				
				//응답
				success:function(result){  
					loginResult = result;
				},
				error : function(request, status, error) {
					alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error + "서버에러");
				}
			}); //ajax
			
			if(loginResult == 'none'){ //로그인 실패시
				alert("아이디 또는 비밀번호가 틀렸습니다.");
				$('#login_member_id').val('');
				$('#login_pwd').val('');
				$('login_member_id').focus();
				return false;
			}
		}); //submit
	}); //ready