package com.example.demo.dto.notice;

import lombok.*;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class NoticeDto {
    private Long notice_no;
    private String title;
    private String content;
    private String writer;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime reg_date;
    private Long view_cnt;

    public NoticeDto(String title, String content, String writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
    }
    
}
