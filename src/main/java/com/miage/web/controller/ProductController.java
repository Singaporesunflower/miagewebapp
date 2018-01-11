package com.miage.web.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.miage.business.model.Product;
import com.miage.business.service.CategoryService;
import com.miage.business.service.ProductService;

@Controller
@RequestMapping("/product")
public class ProductController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CategoryService categoryService;
	
	// TODO Enable method security
//	@Secured("ADMIN")
//	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(method=RequestMethod.GET)
	public String getAll(Model model) {
		model.addAttribute("products", this.productService.findAll());
		return "product/list";
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(Model model) {
		model.addAttribute("categories", this.categoryService.findAll());
		model.addAttribute("product", new Product());
		return "product/form";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String submitCreate(@Valid @ModelAttribute Product product, BindingResult bindingResult, Model model) {
		
		if (bindingResult.hasErrors()) {
			model.addAttribute("categories", this.categoryService.findAll());
            return "product/form";
        }

		productService.save(product);
		return "redirect:/product";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(@RequestParam("id") Long id, Model model) {
		model.addAttribute("categories", this.categoryService.findAll());
		model.addAttribute("product", this.productService.findById(id));
		return "product/form";
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String submitEdit(@Valid @ModelAttribute Product product, BindingResult bindingResult, Model model) {
		
		if (bindingResult.hasErrors()) {
			model.addAttribute("categories", this.categoryService.findAll());
            return "product/form";
        }
		
		productService.save(product);
		return "redirect:/product";
	}
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable("id") Long id) {
		productService.delete(id);
		return "redirect:/product";
	}

}
