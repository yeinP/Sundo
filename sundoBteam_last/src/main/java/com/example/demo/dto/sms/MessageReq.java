package com.example.demo.dto.sms;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
//@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class MessageReq {
	private String type;
	private String contentType;
	private String countryCode;
	private String from;
	private String content;
	private List<CustomMessage> messages;
	
	
	public MessageReq(String type, String contentType, String countryCode, String from, String content,
			List<CustomMessage> messages) {
		this.type = type;
		this.contentType = contentType;
		this.countryCode = countryCode;
		this.from = from;
		this.content = content;
		this.messages = messages;
	}
}
