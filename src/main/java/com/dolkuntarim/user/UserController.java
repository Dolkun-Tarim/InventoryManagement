package com.dolkuntarim.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.*;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private UserRoleRepository userRoleRepository;

    @GetMapping("/user")
    public String viewUserManagePage() {
        return "user_manage";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "signup_form";
    }

    @PostMapping("/process_register")
    public String processRegister(User user) {
        User existingUserWithEmail=userRepo.getUserByUserEmail(user.getEmail());
        if(existingUserWithEmail!=null)
        { return "register_fail";}
        User existingUserWithUsername=userRepo.getUserByUserEmail(user.getUsername());
        if(existingUserWithUsername!=null)
        {
            { return "register_fail";}
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setUseruuid(UUID.randomUUID().toString());
        user.setEnabled(true);
        userRepo.save(user);
        String username=user.getUsername();
        User newUser=userRepo.getUserByUsername(username);
        UserRole userRole=new UserRole();
        userRole.setUser_id(newUser.getId());
        userRole.setUser_uuid(newUser.getUseruuid());
        userRole.setRole_id(2);
        userRoleRepository.save(userRole);
        return "register_success";
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        List<User> listUsers = (List<User>) userRepo.findAll();
        model.addAttribute("listUsers", listUsers);
        return "users";
    }
}
