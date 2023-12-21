package com.example.demo.controller.cctv;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/temp")
public class Cctv {


    @GetMapping("/Cctv")
    public String CctvVideo(){

        return "resources/temp/=4.m3u8";
    }
}
