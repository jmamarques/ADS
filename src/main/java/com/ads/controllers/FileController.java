package com.ads.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * JMA - 25/10/2021 21:17
 **/
@RestController
@RequestMapping("/ads")
public class FileController {

    @GetMapping()
    public String get() {
        return "Hello";
    }
}
