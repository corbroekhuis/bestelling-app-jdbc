package com.warehouse.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderMvcController {

    // http://localhost:<port>/order
    @GetMapping("/order")
    public String orderPage() {

        System.out.println("Inside orderPage");

        return "order";
    }

    // http://localhost:<port>/order
    @GetMapping({"/","/home"})
    public String homePage() {

        System.out.println("Inside homePage");

        return "order";
    }
}

