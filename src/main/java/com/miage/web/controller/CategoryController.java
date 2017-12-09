package com.miage.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/category")
public class CategoryController {

	@RequestMapping(method=RequestMethod.GET)
	public String getCategories() {
		return "categories";
	}
	
}
