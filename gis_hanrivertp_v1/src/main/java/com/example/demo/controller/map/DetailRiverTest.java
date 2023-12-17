package com.example.demo.controller.map;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DetailRiverTest {
    public static void main(String [] args){
        try {
            // API 엔드포인트 URL
            String apiUrl = "https://api.hrfco.go.kr/52832662-D130-4239-9C5F-730AD3BE6BC6/waterlevel/list/10M/1202630/202311041520/202311051520.json";

            // URL 객체 생성
            URL url = new URL(apiUrl);

            // HTTP 연결 생성
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // 응답 코드 확인
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                // HTTP GET 요청 성공

                // 응답 내용을 읽어들이기 위한 BufferedReader 생성
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                // 응답 내용을 읽어들임
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // JSON 데이터 출력
                System.out.println(response.toString());

                // 여기에서 JSON 데이터를 파싱하고 원하는 정보를 추출 및 처리할 수 있습니다.
            } else {
                // HTTP GET 요청 실패
                System.out.println("HTTP GET 요청 실패: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
