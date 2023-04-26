package com.musinsa.fashionBoard.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.musinsa.fashionBoard.item.Item;
import com.musinsa.fashionBoard.mapper.ItemMapper;
import com.musinsa.fashionBoard.mapper.User;
import com.musinsa.fashionBoard.mapper.UserMapper;

import javax.servlet.http.HttpSession;


@Controller
public class UserController {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private ItemMapper itemMapper;

	@GetMapping("/") // return 과 달라도 상관 없음
	public String main(Model model, HttpSession session) {
		String id = (String) session.getAttribute("id");
		if (session.getAttribute("id") == null) {
			id = "손님";
		}
		User user = this.userMapper.selectId(id);
		model.addAttribute("user", user);
		List<Item> items = this.itemMapper.selectAllItem();
		model.addAttribute("items", items);
		model.addAttribute("id", id);
		System.out.println(items.toString());
		return "/index";
	}

	@GetMapping("/signup")
	public String signUpForm() {
		return "signup";
	}

	@PostMapping("/signup")
	public String signUp(User user, Model model) {
		LocalDateTime createDate = LocalDateTime.now();
		user.setDate(createDate);

		userMapper.save(user);
		if (user.getRole().equals("ROLE_SELLER")) {
			int seller_id = userMapper.getMaxUserId();
			userMapper.saveSale(seller_id);
		}

		return "signin";
	}

	@GetMapping("/signin")
	public String signInForm() {
		return "signin";
	}

	@PostMapping("/signin")
	public String signIn(@RequestParam("username") String id, User user, Model model, HttpSession session) {
		User loginResult = userMapper.login(user);

		System.out.println(id);

		if (loginResult != null) {
			// login 성공
			session.setAttribute("id", id);
			session.setMaxInactiveInterval(60*1);
			model.addAttribute("user", loginResult);
			model.addAttribute("id", id);
			List<Item> items = this.itemMapper.selectAllItem();
			model.addAttribute("items", items);
			System.out.println("로그인 성공!");
			return "index";
		} else {
			// login 실패
			System.out.println("로그인 실패!");
			return "signup";
		}
	}

	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		System.out.println("로그아웃 성공!");
		return "redirect:/";
	}

}
