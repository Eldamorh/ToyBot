package com.eldamorh.toybot.controller;

import com.eldamorh.toybot.model.User;
import com.eldamorh.toybot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @GetMapping(value="/user/home")
    public ModelAndView userHome(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
        modelAndView.addObject("username","Username: " + user.getUserName() + "\n");
        modelAndView.addObject("email", "Email: " + user.getEmail() + "\n");
        modelAndView.addObject("welcome", "Welcome " + user.getUserName() + "/" + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
        modelAndView.addObject("adminMessage","Content Available Only for Users with User Role");
        modelAndView.setViewName("user/home");
        return modelAndView;
    }
}
