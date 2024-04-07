package com.School.SchoolValleyProject.controller;

import com.School.SchoolValleyProject.Model.Contact;
import com.School.SchoolValleyProject.Services.ServiceContact;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Slf4j
@Controller

public class ContactController  {

//    @Autowired
//    ContactProxy contactProxy;
    private ServiceContact serviceContact;

    @RequestMapping("/contact")

    public String DispalyContactPage(Model model)
    {
        model.addAttribute("contact",new Contact());
        return "contact.html";
    }
    @Autowired
    public ContactController(ServiceContact serviceContact) {
        this.serviceContact = serviceContact;
    }

    @RequestMapping(value = "/saveMsg",method = POST)

    public String saveMessage(@Valid @ModelAttribute("contact")  Contact contact, Errors errors)
    {
        if(errors.hasErrors())
        {
            log.error("Contact Form validation failed due to : " +errors.toString());
            return "contact.html";
        }
        serviceContact.saveMessage(contact);

        return "redirect:/contact";
    }

//   @RequestMapping("/displayMessages")
//
//    public ModelAndView displaymessage(Model model)
//    {
//        List<Contact> contactMsgs=serviceContact.findMsgsWithOpenStatus();
//        ModelAndView modelAndView= new ModelAndView("/messages.html");
//        modelAndView.addObject("contactMsgs",contactMsgs);
//
//       return modelAndView;
//    }


    @RequestMapping("/displayMessages/page/{pageNum}")
    public ModelAndView displayMessages(Model model,
                                        @PathVariable(name = "pageNum") int pageNum,@RequestParam("sortField") String sortField,
                                        @RequestParam("sortDir") String sortDir) {
        Page<Contact> msgPage = serviceContact.findMsgsWithOpenStatus(pageNum,sortField,sortDir);
        List<Contact> contactMsgs = msgPage.getContent();
        ModelAndView modelAndView = new ModelAndView("messages.html");
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", msgPage.getTotalPages());
        model.addAttribute("totalMsgs", msgPage.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        modelAndView.addObject("contactMsgs",contactMsgs);
        return modelAndView;
    }

//
//    @GetMapping("/getMessages")
//    public List<Contact>getMessages(@RequestParam("status")String status)
//    {
//        return contactProxy.getMessagesByStatus(status);
//    }
    @RequestMapping(value = "/closeMsg",method = GET)
    public String closeMsg(@RequestParam int id)
    {
        serviceContact.UpdateMsgStatus(id);

        return "redirect:/displayMessages/page/1?sortField=name&sortDir=desc";

    }
}
