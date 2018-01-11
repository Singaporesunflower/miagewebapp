package com.miage.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.miage.business.model.Category;
import com.miage.business.service.CategoryService;

@Controller
@RequestMapping("/category")
public class CategoryController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);

	private final CategoryService categoryService;

	@Autowired
	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String getAll(Model model) {
		model.addAttribute("categories", this.categoryService.findAll());
		return "category/list";
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String createModal(Model model) {
		model.addAttribute("category", new Category());
		return "category/formModal";
	}
	
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String modal(@PathVariable("id") Long id, Model model) {
		final Category category = this.categoryService.findById(id);
		model.addAttribute("category", category);
		return "category/formModal";
	}
}
