package com.miage.web.controller;

import javax.validation.Valid;

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
import com.miage.business.service.ProductService;

@Controller
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	// TODO Enable method security
	//@Secured("ADMIN") // securedEnabled = true
	//@PreAuthorize("hasAuthority('ADMIN')") // prePostEnabled = true
	@RequestMapping(method=RequestMethod.GET)
	public String findAll(Model model) {
		model.addAttribute("products", productService.findAll());
		return "product/list";
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(Model model) {
		model.addAttribute("product", new Product());
		return "product/form";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String submitCreate(@Valid @ModelAttribute Product product, BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
            return "product/form";
        }

		productService.save(product);
		return "redirect:/product";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String editForm(@RequestParam("id") Long id, Model model) {
		model.addAttribute("product", productService.findById(id));
		return "product/form";
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String submitEditForm(@Valid @ModelAttribute Product product, BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
            return "product/form";
        }
		
		productService.save(product);
		return "redirect:/product";
	}
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String deleteProduct(@PathVariable("id") Long id) {
		productService.delete(id);
		return "redirect:/product";
	}

}
