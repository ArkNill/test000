package kr.com.inspect.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.com.inspect.dao.LoginDao;
import kr.com.inspect.dto.Member;

@Repository
public class LoginDaoImpl implements LoginDao{
	
	@Autowired
	private SqlSession sqlSession;
	private final String memberNs = "MemberMapper.";

	/* 회원가입 */
	@Override
	public int registerMember(Member member) {
		int result = 0;
		result = sqlSession.insert(memberNs+"registerMember", member);
		return result;
	}
	
	/* 로그인 */
	@Override
	public Member login(Member member) {
		Member result = null;
		result = sqlSession.selectOne(memberNs+"login", member);
		return result;
	}
	
	/* 아이디 중복확인 */
	@Override
	public int IdCheck(String member_id) {
		int result = 0;
		result = sqlSession.selectOne(memberNs+"idCheck", member_id);
		return result;
	}
	
}
