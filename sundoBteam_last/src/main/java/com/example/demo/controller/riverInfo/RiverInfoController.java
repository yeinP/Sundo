package com.example.demo.controller.riverInfo;

import com.example.demo.service.ExcelToJsonService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/River")
public class RiverInfoController {

    @Autowired
    ExcelToJsonService excelToJsonService;

    @GetMapping("/load")
    public String load(Model model,@RequestParam int id){
        model.addAttribute("id", id);
        return "RiverMap/riverInfo";
    }

    @GetMapping("/Info/{id}")
    @ResponseBody
    public String RiverInfo(@PathVariable String id){

        String reid = id+".0";
        List<Map<String, String>> excelData = excelToJsonService.readExcelFile("C:\\_zak\\RiverInfo.xlsx", reid);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonData = objectMapper.writeValueAsString(excelData);
            List<String> jsonDataList = Collections.singletonList(jsonData);
            return jsonDataList.get(0);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }


    }


    @GetMapping("/realtimeInfo/rainFall")
    public void realtimeRain() {}
    
    @GetMapping("/realtimeInfo/waterLevel")
    public void realtimeWaterLv() {}



}
