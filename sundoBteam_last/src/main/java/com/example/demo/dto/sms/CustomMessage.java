package com.example.demo.dto.sms;

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
public class CustomMessage {
	
	private String to;
	private String signNum;
	
	public CustomMessage(String to) {
		super();
		this.to = to;
		this.signNum = String.valueOf(signNum()); // 6자리의 난수 생성
	}
	
	
	public int signNum() { 
	 int randomNum = (int) (Math.random() * (999999 - 100000 + 1)) + 100000;
	 return randomNum;
	}
	
}
