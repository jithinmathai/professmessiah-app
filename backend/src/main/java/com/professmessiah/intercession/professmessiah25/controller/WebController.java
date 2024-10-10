package com.professmessiah.intercession.professmessiah25.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping(value = {"/{path:^(?!api|.*\\..*$).*$}", "/{path:^(?!api|.*\\..*$).*$}/**"})
    public String forwardToFrontend() {
        // This forwards any path that doesn't match API or static resources to index.html
        return "forward:/index.html";
    }
}