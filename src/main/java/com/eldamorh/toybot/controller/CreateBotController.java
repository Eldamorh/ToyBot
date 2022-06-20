package com.eldamorh.toybot.controller;

import com.eldamorh.toybot.model.Bot;
import com.eldamorh.toybot.model.User;
import com.eldamorh.toybot.service.BotService;
import com.eldamorh.toybot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class CreateBotController {

    @Autowired
    private UserService userService;

    @Autowired
    private BotService botService;

    @GetMapping(value="/user/createBot")
    public ModelAndView createBot(){
        ModelAndView modelAndView = new ModelAndView();
        Bot bot = new Bot();
        modelAndView.addObject("bot", bot);
        modelAndView.setViewName("user/create-bot");
        return modelAndView;
    }

    @PostMapping(value = "/user/createBot")
    public ModelAndView createNewBot(@Valid Bot bot, BindingResult bindingResult, Authentication user) {
        String s = user.getName();
        System.out.println("State " + bot.getState());
        System.out.println("Currency " + bot.getCurrency());
        System.out.println("DIp " + bot.getDip_threshold());
        System.out.println("API " + bot.getApi_key());
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("user/create-bot");
        } else {
            botService.saveBot(bot,Long.valueOf(userService.findUserByUserName(user.getName()).getId()));
            modelAndView.addObject("successMessage", "Bot has been created successfully");
            modelAndView.addObject("bot", new Bot());
            modelAndView.setViewName("user/create-bot");

        }
        return modelAndView;
    }
}