package com.springapp.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class HelloController {
	@RequestMapping(method = RequestMethod.GET)
	public String testGet(ModelMap model) {
		model.addAttribute("message", "Hello GET!");
		return "hello";
	}

    @RequestMapping(method = RequestMethod.POST)
    public String testPost(ModelMap model) {
        model.addAttribute("message", "Hello POST!");
        return "hello";
    }

    @RequestMapping(method = RequestMethod.PUT)
    public String testPut(ModelMap model) {
        model.addAttribute("message", "Hello PUT!");
        return "hello";
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public String testDelete(ModelMap model) {
        model.addAttribute("message", "Hello DELETE!");
        return "hello";
    }
}