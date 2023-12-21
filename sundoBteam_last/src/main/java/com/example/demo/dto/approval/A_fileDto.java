package com.example.demo.dto.approval;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@ToString
public class A_fileDto {
    private int fno;
    private int a_no;
    private String ogFile_name;
    private String file_name;
    private String file_path;
    private int file_size;
    private String created_date;

    public A_fileDto(int a_no,  String ogFile_name, String file_name, String file_path, int file_size) {
        this.a_no = a_no;
        this.ogFile_name = ogFile_name;
        this.file_name = file_name;
        this.file_path = file_path;
        this.file_size = file_size;
    }
}
