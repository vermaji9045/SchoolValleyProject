package com.School.SchoolValleyProject.controller;
import com.School.SchoolValleyProject.Model.Holiday;
import com.School.SchoolValleyProject.repository.HolidayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
public class HolidayContoller {


@Autowired
  private  HolidayRepository holidayRepository;

    @GetMapping("/holidays/{display}")
    public String DisplayHoliday(@PathVariable String display, Model model)
    {

        if (null!= display && display.equals("all"))
        {
            model.addAttribute("festival",true);
            model.addAttribute("federal",true);
        }
        else   if (null!= display && display.equals("festival"))
        {
            model.addAttribute("festival",true);
        }else   if (null!= display && display.equals("federal"))
        {

            model.addAttribute("federal",true);
        }

       Iterable<Holiday> holidays= holidayRepository.findAll();

        List<Holiday>holidayList= StreamSupport.stream(holidays.spliterator(),false).collect(Collectors.toList());
        Holiday.Type[] types=Holiday.Type.values();

        for (Holiday.Type type: types)
        {
         model.addAttribute(type.toString(),(holidayList.stream().filter(holiday ->
                 holiday.getType().equals(type)).collect(Collectors.toList())));

        }

        return "holidays.html";
    }
}