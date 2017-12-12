package com.miage.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.miage.business.model.Person;
import com.miage.business.service.PersonService;

@Controller
@RequestMapping("/person")
public class PersonController {

	@Autowired
	PersonService personService;
		
	@RequestMapping(method=RequestMethod.GET)
	public String findAll(Model model) {
		model.addAttribute("persons", personService.findAll());
		return "person/list";
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(Model model) {
		model.addAttribute("person", new Person());
		return "person/form";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String submitCreate(@Valid @ModelAttribute Person person, BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
            return "person/form";
        }

		personService.save(person);
		return "redirect:/person";
	}

}
