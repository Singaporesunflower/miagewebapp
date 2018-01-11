package com.miage.rest.controller;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.miage.business.model.Category;
import com.miage.business.service.CategoryService;

@RestController // or @Controller with @ResponseBody on each method
@RequestMapping(value="/restcategory")
public class CategoryRestController {
    
    @Autowired
    private CategoryService categoryService;

    /**
     * Get category
     * @param id category id
     * @return result containing categoryList in JSON format
     */
    @RequestMapping(method = RequestMethod.GET)
	//@ResponseBody
	public Collection<Category> getAll() {
    	final Collection<Category> categories = this.categoryService.findAll();
    	return categories;
    }
    
    /**
     * Get category
     * @param id category id
     * @return result containing categoryList in JSON format
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Category get(@PathVariable Long id) {
    	return this.categoryService.findById(id);
    }
    
    /**
     * Create category
     * @param category 
     * @return result containing category in JSON format
     */
    @RequestMapping(method = RequestMethod.POST)
    public Category create(@Valid @RequestBody Category category, BindingResult bindingResult) {
        return this.categoryService.create(category);
    }
    
    /**
     * Update category
     * @param id category id
     * @param category 
     * @return result containing category in JSON format
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public Category update(@PathVariable Long id, @Valid @RequestBody Category category) {
        return this.categoryService.update(id, category);
    }
    
    /**
     * Delete category
     * @param id category id
     * @return result with success tag
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public boolean delete(@PathVariable Long id) {
        this.categoryService.delete(id);
        return true;
    }
}
