package com.devops.deploy_app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * HomeController
 *
 * @author hieuvv
 * @version 01-00
 * @since 5/31/2024
 */
@RestController
@RequestMapping("/home")
public class HomeController {
    @GetMapping
    public String home() {
        return "Mịa Hào";
    }
}
