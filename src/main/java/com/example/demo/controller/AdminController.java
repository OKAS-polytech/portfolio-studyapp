package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
	// /admintop にアクセスしたら admintop.html を返す
    @GetMapping("/admintop")
    public String admintop() {
        return "admintop";
    }
}
