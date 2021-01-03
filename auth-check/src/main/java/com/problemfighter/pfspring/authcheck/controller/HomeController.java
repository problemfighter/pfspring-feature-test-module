package com.problemfighter.pfspring.authcheck.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String bismillah(){
        return "Bismillah. Auth check: Home Controller.";
    }
}
