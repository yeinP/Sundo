package com.example.demo.controller.account;

import com.example.demo.dto.account.UserDto;
import com.example.demo.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/account")
public class RegisterController {

	private final UserServiceImpl userService;

	@Autowired
	public RegisterController(UserServiceImpl userService) {
		this.userService = userService;
	}

	@GetMapping("/user")
	public String registerForm() {
		return "account/registerForm";
	}

	@PostMapping("/user")
	public String addUserOne(UserDto userDto, RedirectAttributes ratt) {
		String userId = userDto.getId();
		String userPw = userDto.getPassword();
		
		if (!userCheck(userId, userPw)) {
			ratt.addFlashAttribute("msg", "REG_USER_ERR");
			System.out.println("msg: REG_USER_ERR");
			return "redirect:/account/user";
		}
		this.userService.registerUser(userDto);
		ratt.addFlashAttribute("msg", "REG_USER_OK");
		System.out.println("msg: REG_USER_OK");
		return "redirect:/main";
	}

	private boolean userCheck(String id, String password) {
		Integer user = this.userService.isUser(id);
		System.out.println("user: "+user);
		return  user == null || user == 0;
	}

}
