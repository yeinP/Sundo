package com.example.demo.controller.account;

import com.example.demo.dto.account.UserDto;
import com.example.demo.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

	@Autowired
	private final UserServiceImpl userService;

	@Autowired
	public LoginController(UserServiceImpl userService) {
		this.userService = userService;
	}


	@GetMapping("/login")
	public String loginForm() {
		return "account/loginForm";
	}
	
	@PostMapping("/login")
	public String loginSubmit(String id, String pw, Model model, HttpServletRequest request) {
		if (!loginCheck(id, pw)) {
			model.addAttribute("msg", "LOG_INFO_CHK");
			return "redirect:/#";
		}

		String userRole = this.userService.getUserRole(id);
		System.out.println("userRole: " + userRole);
		HttpSession session = request.getSession();
		session.setAttribute("userRole", userRole);
		session.setAttribute("loggedIn", true); // 사용자가 로그인 상태임을 표시
		return "redirect:/";
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
		return "redirect:/main";
	}

	private boolean loginCheck(String id, String pw) {
	    UserDto user = this.userService.checkUser(id);
	    if (user == null || (user.getPassword() != null && !user.getPassword().equals(pw))) {
	        return false;
	    }
	    return true;
	}


}
