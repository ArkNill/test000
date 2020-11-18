package kr.com.inspect.controller;

import java.awt.print.Printable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.com.inspect.dto.Member;
import kr.com.inspect.service.LoginService;

@Controller
public class MemberController {

	@Autowired
	private LoginService loginService;

	/* 회원가입 */
	@ResponseBody
	@RequestMapping(value = "/registerMember", produces = "application/text; charset=utf8")
	public String registerMember(Member member, Model model) {
		String msg = "회원가입에 실패하였습니다.";
		int result = loginService.registerMember(member);
		if (result == 1) {
			msg = "회원가입 완료! 로그인해주세요.";
		}
		return msg;
	}

	/* 아이디 중복 체크 */
	@ResponseBody
	@PostMapping("/idCheck")
	public String IdCheck(HttpServletRequest request) {
		String member_id = request.getParameter("member_id");
		int result = loginService.IdCheck(member_id);
		System.out.println("asfas");
		return Integer.toString(result);
	}
	
	/* 아이디와 비밀번호로 회원 체크 */
	@ResponseBody
	@PostMapping("/isMember")
	public String isMember(Member member, HttpSession session) {
		try {
			if (session.getAttribute("loginId") != null) {
				session.removeAttribute("loginId");
			}
			Member result = loginService.loginMember(member);
			session.setAttribute("loginId", result.getMember_id());
			String role = result.getMember_id();
			if(role.equals("admin")) {
				session.setAttribute("role", "admin");
				return "admin";
			}else {
				session.setAttribute("role", "member");
				return "member";
			}
		} catch (NullPointerException e) {
			return "none";
		}
	}

	/* 로그인 */
	@RequestMapping("/loginMember")
	public String loginMember(HttpSession session) throws Exception {
		if(session.getAttribute("loginId") != null) { //로그인 상태 확인
			return "/main";
		}
		else {
			return "redirect:index.jsp";
		}
	}

	/* 로그아웃 */
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate(); //모든 세션 초기화
		// session.setAttribute("loginId",null); 으로 해줘도 된다.
		return "redirect:index.jsp";
	}

	/* 회원가입 페이지 이동 */
	@GetMapping("/register")
	public String moveToElasticPage() {
		return "/register";
	}

	/* 회원정보 가져와서 회원 목록 페이지로 이동 */
	@GetMapping("/memberList")
	public String getMember() {
		return "member/getMemberList";
	 
	}
	
	/* 2020-11-13 */
	//testins
	//tstsadfasdfasdfas
	///easetsdfqasdfasfdasffdgdfgdgfdgdfdgfdfgdgfdgfdsafasdfsafdas
	///asdfasdfasdfasfds
	//asdfasdfasdfasdfs
	//asdfasdfasdfasfawd
	//1118 build/asdssadfasdfsfasdfafsdfas
	//llllllooo
	//youijdhqwe
	//gjeiqodkeodlfj
}