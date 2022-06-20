package com.eldamorh.toybot.service;

import com.eldamorh.toybot.model.Bot;
import com.eldamorh.toybot.model.Role;
import com.eldamorh.toybot.model.User;
import com.eldamorh.toybot.repository.BotRepository;
import com.eldamorh.toybot.repository.RoleRepository;
import com.eldamorh.toybot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;

@Service
public class BotService {
    private BotRepository botRepository;
    private UserService userService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public BotService(BotRepository botRepository,
                      UserService userService,
                      BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.botRepository = botRepository;
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    public Bot saveBot(Bot bot, Long UserID) {
        bot.setActive(true);
        bot.setApi_key(bCryptPasswordEncoder.encode(bot.getApi_key()));
        bot.setSecret_key(bCryptPasswordEncoder.encode(bot.getSecret_key()));
//        bot.setCurrency(bot.getCurrency());
//        bot.setDIP_THRESHOLD(bot.getDIP_THRESHOLD());
//        bot.setPROFIT_THRESHOLD(bot.getPROFIT_THRESHOLD());
//        bot.setSTOP_LOSS_THRESHOLD(bot.getSTOP_LOSS_THRESHOLD());
//        bot.setUPWARD_TREND_THRESHOLD(bot.getUPWARD_TREND_THRESHOLD());
//        bot.setState(bot.getState());
        userService.addBot(bot,UserID);
        return botRepository.save(bot);
    }
}
