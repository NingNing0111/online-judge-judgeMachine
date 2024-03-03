package com.ningning0111.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Project: com.ningning0111.controller
 * @Author: pgthinker
 * @GitHub: https://github.com/ningning0111
 * @Date: 2024/3/2 18:19
 * @Description:
 */
@RestController
public class CheckController {
    @GetMapping("/health")
    public String check(){
        return "ok";
    }
}
