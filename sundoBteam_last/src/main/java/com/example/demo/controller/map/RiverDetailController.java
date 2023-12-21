package com.example.demo.controller.map;

import com.example.demo.dto.riverCode.CodeDto;
import com.example.demo.dto.riverCode.RainFallDto;
import com.example.demo.dto.riverCode.RiverCodeDto;
import com.example.demo.service.RiverCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Optional;


@Controller
@RequestMapping("/hanRiver")
public class RiverDetailController {

    @Autowired
    RiverCodeService riverCodeService;

    @GetMapping("/detail/river/{wlobscd}")
    public String detailRiver(@PathVariable String wlobscd, Model model) {
        riverCodeService.updateWlobscdList();
        Optional<CodeDto> wlobscdDto = riverCodeService.getRiverInfoByWlobscd(wlobscd);
        model.addAttribute("wlobscdInfo", wlobscdDto);
        return "detail/river";
    }

    @GetMapping("detail/rainFall/{rfobscd}")
    public String detailRainFall(@PathVariable String rfobscd, Model model){
        riverCodeService.updateRcodeList();
        Optional<CodeDto> rainFallDto = riverCodeService.getRiverInfoByRfobscd(rfobscd);
        model.addAttribute("rfobscdInfo", rainFallDto);
        return "detail/rainFall";
    }

    @GetMapping("detail/dam/{dmobscd}")
    public String detailDam(@PathVariable String dmobscd, Model model){
        riverCodeService.updateDmobscdList();
        Optional<CodeDto> damDto = riverCodeService.getRiverInfoByDmobscd(dmobscd);
        model.addAttribute("damInfo", damDto);
        return "detail/dam";
    }

//    @GetMapping("/detail/river/{r_code}")
//    public String detailRiver(@PathVariable int r_code, Model model) {
//        RiverCodeDto riverCodeDto = riverCodeService.selectRcode(r_code);
//        model.addAttribute("riverInfo", riverCodeDto);
//        return "detail/river";
//    }
//
//    @GetMapping("detail/rainFall/{rfobscd}")
//    public String detailRainFall(@PathVariable String rfobscd, Model model){
//        riverCodeService.updateRcodeList();
//        Optional<RainFallDto> rainFallDto = riverCodeService.getRiverInfoByRfobscd(rfobscd);
//        model.addAttribute("rfobscdInfo", rainFallDto);
//        return "detail/rainFall";
//    }
}
