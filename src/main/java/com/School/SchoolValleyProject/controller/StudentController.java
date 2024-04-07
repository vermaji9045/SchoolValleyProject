package com.School.SchoolValleyProject.controller;

import com.School.SchoolValleyProject.Model.Person;
import com.School.SchoolValleyProject.repository.CoursesRepository;
import com.School.SchoolValleyProject.repository.PersonRepository;
import com.School.SchoolValleyProject.repository.ValleyClassRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequestMapping("student")
public class StudentController {

    @Autowired
    ValleyClassRepository valleyClassRepository;
    @Autowired
    PersonRepository personRepository;

    @Autowired
    CoursesRepository coursesRepository;

    @GetMapping("/displayCourses")
    public ModelAndView displayCourses(Model model, HttpSession httpSession)
    {
        Person person=(Person) httpSession.getAttribute("loggedInPerson");
        ModelAndView modelAndView=new ModelAndView("courses_enrolled.html");
        modelAndView.addObject("person",person);
        return modelAndView;
    }
}
