package com.example.demo.controller.map;

import com.example.demo.service.AddrService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/Addr")
public class RiverAddController {

    @Autowired
    AddrService addrService;

    @GetMapping("/lat")
    @ResponseBody
    public List<ObjectNode> lat() throws JsonProcessingException {
        return addrService.latLong();
    }



}
