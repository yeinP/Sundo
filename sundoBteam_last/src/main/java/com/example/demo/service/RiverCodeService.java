package com.example.demo.service;

import com.example.demo.dto.riverCode.CodeDto;
import com.example.demo.dto.riverCode.RiverCodeDto;
import com.example.demo.repository.RiverCodeDao;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RiverCodeService {

    @Autowired
    private RiverCodeDao riverCodeDao;


    public List<RiverCodeDto> riverCode(){return this.riverCodeDao.riverCode();}

    public RiverCodeDto selectRcode(int r_code) {return riverCodeDao.selectRcode(r_code);}

    @Autowired
    RestTemplate restTemplate;


    @Value("http://api.hrfco.go.kr/8794F4D7-ED45-43EB-8E2F-1B721623F802/rainfall/info.json")
    private String rainFallapiUrl;
    private List<CodeDto> rfobscdList = new ArrayList<>();

    public void updateRcodeList() {
        String apiResponse = restTemplate.getForObject(rainFallapiUrl, String.class);
        rfobscdList = getRainFallDtoListFromJson(apiResponse);
    }

    private List<CodeDto> getRainFallDtoListFromJson(String jsonResponse) {
        List<CodeDto> rfobscdList = new ArrayList<>();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonResponse);
            for (JsonNode contentNode : jsonNode.path("content")) {
                CodeDto codeDto = new CodeDto();
                codeDto.setRfobscd(contentNode.path("rfobscd").asText());


                rfobscdList.add(codeDto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rfobscdList;

    }

    public Optional<CodeDto> getRiverInfoByRfobscd(String rfobscd) {
        return rfobscdList.stream()
                .filter(codeDto -> rfobscd.equals(codeDto.getRfobscd()))
                .findFirst();
    }


    @Value("http://api.hrfco.go.kr/8794F4D7-ED45-43EB-8E2F-1B721623F802/waterlevel/info.json")
    private String waterLevelApiUrl;

    private List<CodeDto> wlobscdList = new ArrayList<>();

    public void updateWlobscdList() {
        String apiResponse = restTemplate.getForObject(waterLevelApiUrl, String.class);
        wlobscdList = getwlobscdListFromJson(apiResponse);
    }

    private List<CodeDto> getwlobscdListFromJson(String jsonResponse) {
        List<CodeDto> wlobscdDtoList = new ArrayList<>();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonResponse);
            for (JsonNode contentNode : jsonNode.path("content")) {
                CodeDto codeDto = new CodeDto();
                codeDto.setWlobscd(contentNode.path("wlobscd").asText());


                wlobscdDtoList.add(codeDto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wlobscdDtoList;
    }

    public Optional<CodeDto> getRiverInfoByWlobscd(String wlobscd) {
        return wlobscdList.stream()
                .filter(codeDto -> wlobscd.equals(codeDto.getWlobscd()))
                .findFirst();
    }


    @Value("http://api.hrfco.go.kr/8794F4D7-ED45-43EB-8E2F-1B721623F802/dam/info.json")
    private String damApiUrl;

    private List<CodeDto> damList = new ArrayList<>();

    public void updateDmobscdList() {
        String apiResponse = restTemplate.getForObject(damApiUrl, String.class);
        damList = getDmobscdListFromJson(apiResponse);
    }

    private List<CodeDto> getDmobscdListFromJson(String jsonResponse) {
        List<CodeDto> dmobscdDtoList = new ArrayList<>();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonResponse);
            for (JsonNode contentNode : jsonNode.path("content")) {
                CodeDto codeDto = new CodeDto();
                codeDto.setDmobscd(contentNode.path("dmobscd").asText());


                dmobscdDtoList.add(codeDto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dmobscdDtoList;
    }

    public Optional<CodeDto> getRiverInfoByDmobscd(String dmobscd) {
        return damList.stream()
                .filter(codeDto -> dmobscd.equals(codeDto.getDmobscd()))
                .findFirst();
    }

//    @Autowired
//    RestTemplate restTemplate;
//
//
//    @Value("https://api.hrfco.go.kr/8794F4D7-ED45-43EB-8E2F-1B721623F802/rainfall/info.json")
//    private String apiUrl;
//
//    public List<String> getRfobsdcList() {
//        String apiResponse = restTemplate.getForObject(apiUrl, String.class);
//        return getRfobscdListFromJson(apiResponse);
//    }
//
//    private List<String> getRfobscdListFromJson(String jsonResponse) {
//        List<String> rfobscdList = new ArrayList<>();
//
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            JsonNode jsonNode = objectMapper.readTree(jsonResponse);
//            for (JsonNode contentNode : jsonNode.path("content")) {
//                String rfobscd = contentNode.path("rfobscd").asText();
//                rfobscdList.add(rfobscd);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return rfobscdList;
//    }
//
//    // API로부터 받아온 데이터를 저장할 리스트
//    private List<RainFallDto> rcodeList = new ArrayList<>();
//
//    // API에서 받아온 데이터를 리스트에 저장하는 메서드
//    public void updateRcodeList() {
//        String apiResponse = restTemplate.getForObject(apiUrl, String.class);
//        rcodeList = getRainFallDtoListFromJson(apiResponse);
//    }
//
//    // API 응답에서 RainFallDto 리스트를 추출하는 메서드
//    private List<RainFallDto> getRainFallDtoListFromJson(String jsonResponse) {
//        List<RainFallDto> rainFallDtoList = new ArrayList<>();
//
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            JsonNode jsonNode = objectMapper.readTree(jsonResponse);
//            for (JsonNode contentNode : jsonNode.path("content")) {
//                // contentNode에서 필요한 데이터를 추출하여 RainFallDto 객체를 생성
//                RainFallDto rainFallDto = new RainFallDto();
//                rainFallDto.setRfobscd(contentNode.path("rfobscd").asText());
//                // 필요한 다른 데이터도 추출하여 설정
//
//                rainFallDtoList.add(rainFallDto);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return rainFallDtoList;
//    }
//
//    public Optional<RainFallDto> getRiverInfoByRfobscd(String rfobscd) {
//        // 리스트에서 특정 rfobscd를 가진 RainFallDto를 찾기
//        return rcodeList.stream()
//                .filter(rainFallDto -> rfobscd.equals(rainFallDto.getRfobscd()))
//                .findFirst();
//    }


}

