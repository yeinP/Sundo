package com.example.demo.service;

import com.example.demo.dto.cctvAddr.CctvAddr;
import com.example.demo.repository.CctvDao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class AddrService {
    @Autowired
    CctvDao cctvDao;
    public List<ObjectNode> latLong() throws JsonProcessingException {
        List<ObjectNode> jsonresult =  new ArrayList<>();;
        List<CctvAddr> list = cctvDao.cctv();
        ObjectNode rootNode = null;
        ObjectMapper objectMapper = new ObjectMapper();
        for(CctvAddr cctvAddr : list) {
            String apikey = "E968F8D8-C4D0-33B0-8F1D-392239886745";
            String searchType = "parcel";
            String searchAddr = cctvAddr.getAddr();
            String epsg = "epsg:4326";

            StringBuilder sb = new StringBuilder("https://api.vworld.kr/req/address");
            sb.append("?service=address");
            sb.append("&request=getCoord");
            sb.append("&format=json");
            sb.append("&crs=" + epsg);
            sb.append("&key=" + apikey);
            sb.append("&type=" + searchType);
            sb.append("&address=" + URLEncoder.encode(searchAddr, StandardCharsets.UTF_8));

            try {
                URL url = new URL(sb.toString());
                BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
                String line;
                StringBuilder response = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();
                 rootNode = (ObjectNode) objectMapper.readTree(response.toString());
                rootNode.put("name", cctvAddr.getName());
                rootNode.put("code", cctvAddr.getCode());
                // 콘솔에 응답 출력
                jsonresult.add(rootNode);


            } catch (IOException e) {
                throw new RuntimeException(e);

            }
        }
        return jsonresult;

    }
}
