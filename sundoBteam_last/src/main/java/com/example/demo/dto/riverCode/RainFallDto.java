package com.example.demo.dto.riverCode;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RainFallDto {
    public String rfobscd;
    public String obsnm;
    public double lon;
    public double lat;
}
