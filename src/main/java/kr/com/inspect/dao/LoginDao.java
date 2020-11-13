package kr.com.inspect.dao;

import kr.com.inspect.dto.Member;

public interface LoginDao {
	/* 회원가입 */
	public int registerMember(Member member);

	/* 로그인 */
	public Member login(Member member);

	/* 아이디 중복확인 */
	public int IdCheck(String member_id);
}
