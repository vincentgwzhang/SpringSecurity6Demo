package com.example.securitydemo.controller;

import com.example.securitydemo.dto.DeveloperDTO;
import com.example.securitydemo.service.DeveloperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    private DeveloperService developerService;

    @Autowired
    public HomeController(DeveloperService developerService) {
        this.developerService = developerService;
    }

    @GetMapping("/")
    public String homePage() {
        return "home";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @Secured("ROLE_DEVELOPER")
    @GetMapping("/developer")
    public String developerPage() {
        return "developer";
    }

    @Secured("ROLE_DEVELOPER")
    @PostMapping("/developer2/{id}")
    public String developer2Page(@PathVariable int id, Model model) {
        DeveloperDTO developerDTO = developerService.getDeveloper(id);
        model.addAttribute("developer", developerDTO);
        return "developer";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/admin")
    public String adminPage() {
        return "admin";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/admin2")
    public String admin2Page() {
        return "admin";
    }

}
