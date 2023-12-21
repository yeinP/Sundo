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
public class C_userDto {

	private int cuNo;
	private int cuType;
	private String cuName;
	private String cuPhone;
	private Integer cuGender;
	private String cuBirth;
	private String cuAddress;
	
	public C_userDto(int cuType, String cuName, String cuPhone, Integer cuGender, String cuBirth, String cuAddress) {
		this.cuType = cuType;
		this.cuName = cuName;
		this.cuPhone = cuPhone;
		this.cuGender = cuGender;
		this.cuBirth = cuBirth;
		this.cuAddress = cuAddress;
	}
}
