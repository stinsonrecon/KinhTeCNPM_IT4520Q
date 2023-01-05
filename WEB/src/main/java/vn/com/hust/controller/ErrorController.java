package vn.com.hust.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class ErrorController
{
	@RequestMapping("/errorAuthorities")
	public String errorAuthorities() {
		return "/errorAuthorities";
	}

}
