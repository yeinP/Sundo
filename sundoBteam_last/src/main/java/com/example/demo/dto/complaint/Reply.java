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
public class Reply {
	private int rno;
	private int cno;
	private String rtitle;
	private String rcontent;
	private int rcount;
	private String regdate;
	private String revdate;
	
	public Reply(int cno, String rtitle, String rcontent) {
		this.cno = cno;
		this.rtitle = rtitle;
		this.rcontent = rcontent;
	}
}