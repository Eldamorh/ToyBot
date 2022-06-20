package com.eldamorh.toybot.service;

import com.eldamorh.toybot.model.Bot;
import com.eldamorh.toybot.model.User;
import com.eldamorh.toybot.repository.RoleRepository;
import com.eldamorh.toybot.repository.UserRepository;
import com.eldamorh.toybot.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findUserByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public User addBot(Bot bot, Long UserId){
        Optional<User> temp = userRepository.findById(UserId);
        User user = userRepository.findById(UserId).get();
        Set<Bot> bots = user.getBots();
        bots.add(bot);
        user.setBots(bots);
        return userRepository.save(user);

    }

    public User saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(true);
        Role userRole = roleRepository.findByRole("USER");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        return userRepository.save(user);
    }

}