package com.example.demo.controller;

import com.example.demo.dto.riverCode.RainFallDto;
import com.example.demo.service.ExcelToJsonService;
import com.example.demo.service.RainFallListService;
import com.example.demo.service.RiverCodeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainController {
    @Autowired
    RiverCodeService riverCodeService;
    @Autowired
    ExcelToJsonService excelToJsonService;
    @Autowired
    RainFallListService rainFallListService;

    @GetMapping("/")
    public String home(Model model) throws JsonProcessingException {

        List<RainFallDto> rainFallDtos = rainFallListService.getRainFallList();
        model.addAttribute("rainFallList", rainFallDtos);

        return "/basic/info";
    }
}
