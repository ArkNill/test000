package kr.com.inspect.service;

import kr.com.inspect.dto.Member;

public interface LoginService {
	/* 회원가입 */
	public  int registerMember(Member member); 
	
	/* 로그인 */
	public Member loginMember(Member member); 
	
	/* 아이디 중복 체크 */
	public int IdCheck(String member_id);
}
