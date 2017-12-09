package com.miage.web.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.miage.business.model.Person;

@Controller
@RequestMapping("/person")
public class PersonController {

	@RequestMapping(method = RequestMethod.GET)
    public String showFormPerson(Model model) {
    	model.addAttribute("person", new Person());
        return "person/form";
    }
    
    @GetMapping("/results")
    public String showResult() {
        return "person/results";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String checkPersonInfo(@Valid @ModelAttribute Person person, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "person/form";
        }

        return "redirect:/person/results";
    }

}
