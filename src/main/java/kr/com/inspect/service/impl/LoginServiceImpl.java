package kr.com.inspect.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.com.inspect.dao.LoginDao;
import kr.com.inspect.dto.Member;
import kr.com.inspect.service.LoginService;


@Service
public class LoginServiceImpl implements LoginService {
	@Autowired
	private LoginDao logindao;
	
	/* 회원가입 */
	public int registerMember(Member member) {
		return logindao.registerMember(member);
	}
	
	/* 로그인 */
	public Member loginMember(Member member){
		return logindao.login(member);
	}
	
	/* 아이디 중복 체크 */
	public int IdCheck(String userid) {
		return logindao.IdCheck(userid);
	}
}