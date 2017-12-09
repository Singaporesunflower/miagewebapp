package com.miage.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HelloController.class);

	@RequestMapping("/hello")
	public ModelAndView hello(@RequestParam(value = "name", required = false) String name) {

		LOGGER.info("GET: /hello");
		
		ModelAndView view = new ModelAndView("hello");
		view.addObject("name", name);
		return view;
	}
}