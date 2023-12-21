package com.example.demo.service;

import com.example.demo.dto.riverCode.RainFallDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class RainFallListService {
    @Autowired
    RestTemplate restTemplate;

    Date today = new Date();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
    String date = dateFormat.format(today);


    @Value("http://api.hrfco.go.kr/8794F4D7-ED45-43EB-8E2F-1B721623F802/rainfall/info.json")
    public String rainFallInfoApi;

    public List<RainFallDto> getRainFallList() throws JsonProcessingException {
        String apiResponseInfo = restTemplate.getForObject(rainFallInfoApi, String.class);
        List<RainFallDto> rainFallInfoList = getRainFallDtoList(apiResponseInfo);
        return rainFallInfoList;
    }



    public List<RainFallDto> getRainFallDtoList(String jsonResponse) throws JsonProcessingException {
        List<RainFallDto> rainFallDtoList = new ArrayList<>();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(jsonResponse);
        for (JsonNode contentList : jsonNode.path("content")) {
            String addr = contentList.path("addr").asText();
            if(addr.contains("서울") || addr.contains("경기")) {
                String rfobscd = contentList.path("rfobscd").asText();
                String obsnm = contentList.path("obsnm").asText();
                double lon = convertToDouble(contentList.path("lon").asText());
                double lat = convertToDouble(contentList.path("lat").asText());
                RainFallDto rainFallDto = new RainFallDto(rfobscd, obsnm, lon, lat);
                rainFallDtoList.add(rainFallDto);
            }
        }
        return rainFallDtoList;
    }

    private double convertToDouble(String degree) {
        String[] parts = degree.split("-");
        double result = Double.parseDouble(parts[0]) + Double.parseDouble(parts[1])/60.0 + Double.parseDouble(parts[2]) / 3600.0;
        return result;
    }







}