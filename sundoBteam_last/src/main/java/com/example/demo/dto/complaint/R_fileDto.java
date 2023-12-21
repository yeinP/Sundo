package com.example.demo.dto.complaint;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@ToString
public class R_fileDto {
    private int fno;
    private int rno;
    private String ogFile_name;
    private String file_name;
    private String file_path;
    private int file_size;
    private String created_date;

    public R_fileDto(int rno,  String ogFile_name, String file_name, String file_path, int file_size) {
        this.rno = rno;
        this.ogFile_name = ogFile_name;
        this.file_name = file_name;
        this.file_path = file_path;
        this.file_size = file_size;
    }
}
