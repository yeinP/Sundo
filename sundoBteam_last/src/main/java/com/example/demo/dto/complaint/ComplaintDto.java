package com.example.demo.dto.complaint;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ComplaintDto {
	private int cno;
	private String ctitle;
	private String c_content;
	private int c_count;
	private int cuno;
	private String cuname;
	private int secret;
	private String pword;
	private String regDate;
	private String revDate;
	private String c_loc;
	private int status;
	
	public ComplaintDto(String ctitle, String c_content, int cuno, int secret, String pword, String c_loc) {
		this.ctitle = ctitle;
		this.c_content = c_content;
		this.cuno = cuno;
		this.secret = secret;
		this.pword = pword;
		this.c_loc = c_loc;
	}
}