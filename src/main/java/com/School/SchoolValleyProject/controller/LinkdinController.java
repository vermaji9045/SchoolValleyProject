package com.School.SchoolValleyProject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LinkdinController {

    @RequestMapping("/linkedin")
    public String redirectToLinkedInProfile() {
        return "redirect:https://www.linkedin.com/in/sandeep-verma9045/";
    }
}
