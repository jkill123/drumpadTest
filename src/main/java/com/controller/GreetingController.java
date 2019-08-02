package com.controller;

import com.domain.User;
import com.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class GreetingController {
    public static String uploadDir = "/home/jkill/test";

    @GetMapping("/greeting")
    public String greeting(){
        return "greeting";
    }
}
