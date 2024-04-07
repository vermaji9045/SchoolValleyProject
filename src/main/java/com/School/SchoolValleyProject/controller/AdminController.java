package com.School.SchoolValleyProject.controller;

import com.School.SchoolValleyProject.Model.Person;
import com.School.SchoolValleyProject.Model.ValleyClass;
import com.School.SchoolValleyProject.Model.courses;
import com.School.SchoolValleyProject.repository.CoursesRepository;
import com.School.SchoolValleyProject.repository.PersonRepository;
import com.School.SchoolValleyProject.repository.ValleyClassRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("admin")
public class AdminController {

    @Autowired
    ValleyClassRepository valleyClassRepository;
    @Autowired
    PersonRepository personRepository;

    @Autowired
    CoursesRepository coursesRepository;

    @RequestMapping("/displayClasses")
    public ModelAndView displayclasses(Model model)
    {
        List<ValleyClass>ValleyClasses=valleyClassRepository.findAll();
        ModelAndView modelAndView=new ModelAndView("classes.html");
        modelAndView.addObject("ValleyClasses",ValleyClasses);
        modelAndView.addObject("valleyClass",new ValleyClass());
        return modelAndView;
    }

    @PostMapping("/addNewClass")
    public ModelAndView addNewClass(Model model, @ModelAttribute("valleyClass") ValleyClass valleyClass)
    {
        valleyClassRepository.save(valleyClass);
        ModelAndView modelAndView=new ModelAndView("redirect:/admin/displayClasses");
        return modelAndView;
    }
    @RequestMapping("/deleteClass")
    public ModelAndView deleteClass(Model model, @RequestParam int id)
    {
        Optional<ValleyClass>valleyClass=valleyClassRepository.findById(id);
        for(Person person:valleyClass.get().getPersons())
        {
            person.setValleyClass(null);
            personRepository.save(person);
        }
        valleyClassRepository.deleteById(id);
        ModelAndView modelAndView=new ModelAndView("redirect:/admin/displayClasses");
        return modelAndView;
    }


    @GetMapping("/displayStudents")
    public ModelAndView displayStudents(Model model, @RequestParam int classId, HttpSession session,
                                        @RequestParam(value = "error", required = false) String error) {
        String errorMessage = null;
        ModelAndView modelAndView = new ModelAndView("students.html");
        Optional<ValleyClass> valleyClass = valleyClassRepository.findById(classId);
        modelAndView.addObject("valleyClass",valleyClass.get());
        modelAndView.addObject("person",new Person());
        session.setAttribute("valleyClass",valleyClass.get());
        if(error != null) {
            errorMessage = "Invalid Email entered!!";
            modelAndView.addObject("errorMessage", errorMessage);
        }
        return modelAndView;
    }
    @PostMapping("/addStudent")
    public ModelAndView addStudent(Model model, @ModelAttribute("person") Person person, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        ValleyClass eazyClass = (ValleyClass) session.getAttribute("valleyClass");
        Person personEntity = personRepository.readByEmail(person.getEmail());
        if(personEntity==null || !(personEntity.getPerson_id()>0)){
            modelAndView.setViewName("redirect:/admin/displayStudents?classId="+eazyClass.getClassId()
                    +"&error=true");
            return modelAndView;
        }
        personEntity.setValleyClass(eazyClass);
        personRepository.save(personEntity);
        eazyClass.getPersons().add(personEntity);
        valleyClassRepository.save(eazyClass);
        modelAndView.setViewName("redirect:/admin/displayStudents?classId="+eazyClass.getClassId());
        return modelAndView;
    }

    @GetMapping("/deleteStudent")
    public ModelAndView deleteStudent(Model model, @RequestParam int personId, HttpSession session) {
        ValleyClass eazyClass = (ValleyClass) session.getAttribute("valleyClass");
        Optional<Person> person = personRepository.findById(personId);
        person.get().setValleyClass(null);
        eazyClass.getPersons().remove(person.get());
        ValleyClass eazyClassSaved = valleyClassRepository.save(eazyClass);
        session.setAttribute("valleyClass",eazyClassSaved);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/displayStudents?classId="+eazyClass.getClassId());
        return modelAndView;
    }

    @GetMapping("/displayCourses")
    public ModelAndView displayCourses(Model model)
    {
        List<courses>courses=coursesRepository.findAll(Sort.by("name").ascending());
        ModelAndView modelAndView=new ModelAndView("courses_secure.html");
        modelAndView.addObject("courses",courses);
        model.addAttribute("course",new courses());
        return modelAndView;
    }
    @PostMapping("/addNewCourse")
    public ModelAndView addNewCourses(Model model,@ModelAttribute("course") courses course)
    {
        ModelAndView modelAndView=new ModelAndView();
        coursesRepository.save(course);
        modelAndView.setViewName("redirect:/admin/displayCourses");
        return modelAndView;
    }

    @GetMapping("/viewStudents")
    public ModelAndView viewStudents(Model model, @RequestParam int id,HttpSession session,@RequestParam(required = false) String error) {
        String errorMessage = null;
        ModelAndView modelAndView = new ModelAndView("course_students.html");
        Optional<courses> courses = coursesRepository.findById(id);
        modelAndView.addObject("courses",courses.get());
        modelAndView.addObject("person",new Person());
        session.setAttribute("courses",courses.get());
        if(error != null) {
            errorMessage = "Invalid Email entered!!";
            modelAndView.addObject("errorMessage", errorMessage);
        }
        return modelAndView;
    }
    @PostMapping("/addStudentToCourse")
    public ModelAndView addStudentToCourse(Model model, @ModelAttribute("person") Person person,
                                           HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        courses courses = (courses) session.getAttribute("courses");
        Person personEntity = personRepository.readByEmail(person.getEmail());
        if(personEntity==null || !(personEntity.getPerson_id()>0)){
            modelAndView.setViewName("redirect:/admin/viewStudents?id="+courses.getCourseId() +"&error=true");
            return modelAndView;
        }
        personEntity.getCourses().add(courses);
        courses.getPersons().add(personEntity);
        personRepository.save(personEntity);
        session.setAttribute("courses",courses);
        modelAndView.setViewName("redirect:/admin/viewStudents?id="+courses.getCourseId());
        return modelAndView;
    }

    @GetMapping("/deleteStudentFromCourse")
    public ModelAndView deleteStudentFromCourse(Model model, @RequestParam int personId,
                                                HttpSession session)
    {

        courses course=(courses) session.getAttribute("courses");
        Optional<Person>person=personRepository.findById(personId);
        person.get().getCourses().remove(course);
        personRepository.save(person.get());
        session.setAttribute("courses",course);
        ModelAndView modelAndView=new ModelAndView("redirect:/admin/viewStudents?id="+course.getCourseId());

        return modelAndView;

    }
}
