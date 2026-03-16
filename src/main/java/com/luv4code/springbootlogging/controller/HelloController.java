package com.luv4code.springbootlogging.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1")
@RestController
@Slf4j
public class HelloController {

    @GetMapping("/hello")
    public String sayHello() {
        log.info("Received request for /hello");

        log.trace("TRACE: HelloController LOG");
        log.debug("DEBUG: HelloController LOG");
        log.info("INFO: HelloController LOG");
        log.warn("WARN: HelloController LOG");
        log.error("ERROR: HelloController LOG");
        int i = 10 / 0;
        return "Hello SpringBoot!!!";
    }
}
