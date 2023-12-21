package com.example.demo.controller.map;


import com.example.demo.dto.riverCode.RainFallDto;
import com.example.demo.dto.riverCode.RiverCodeDto;
import com.example.demo.service.ExcelToJsonService;
import com.example.demo.service.RainFallListService;
import com.example.demo.service.RiverCodeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/HanRiverMap")
public class HanRiverMapController {

    @Autowired
    RiverCodeService riverCodeService;
    @Autowired
    ExcelToJsonService excelToJsonService;
    @Autowired
    RainFallListService rainFallListService;

    @GetMapping("/marker")
    @ResponseBody
    public List<RiverCodeDto> marker(){

            return riverCodeService.riverCode();
    }

    @GetMapping("/waterLv")
    public String waterLv(Model model,@RequestParam int id){
        model.addAttribute("id",id);
        return "RiverMap/waterLv";
    }

    @GetMapping("/mapTest")
    public String mapTest(){

        return "RiverMap/test1102";
    }
//
//    @GetMapping("FileName")
//    @ResponseBody
//    public String name(@RequestParam int id){
//
//        String filePath = "C:\\Users\\1\\Downloads\\modify_data\\waterLv\\waterLv_" + id + "_1.xls";
//        List<Map<String, Object>> excelData = excelToJsonService.readExcelFile(filePath);
//        ObjectMapper objectMapper = new ObjectMapper();
//        try {
//            return objectMapper.writeValueAsString(excelData);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//            return "Error converting data to JSON";
//        }
//    }
    @GetMapping("/info")
    public String info(Model model) throws JsonProcessingException {
        List<RainFallDto> rainFallDtos = rainFallListService.getRainFallList();
        model.addAttribute("rainFallList", rainFallDtos);

        return "basic/info";
    }

}
