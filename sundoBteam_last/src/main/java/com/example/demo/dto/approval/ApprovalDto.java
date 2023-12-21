package com.example.demo.dto.approval;

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
public class ApprovalDto {
	private int a_no;
	private int a_status;
	private int r_code;
	private String r_name;
	private String a_name;
	private String a_phone;
	private String a_place;
	private String a_purpose;
	private String startDate;
	private String endDate;
	private double a_volume;
	private double a_goal;
	private String regDate;
	private String appDate;
	private String a_comment;
	
	public ApprovalDto(int a_status, int r_code, String a_name, String a_phone, String a_place, String a_purpose,
			String startDate, String endDate, double a_volume, double a_goal) {
		this.a_status = a_status;
		this.r_code = r_code;
		this.a_name = a_name;
		this.a_phone = a_phone;
		this.a_place = a_place;
		this.a_purpose = a_purpose;
		this.startDate = startDate;
		this.endDate = endDate;
		this.a_volume = a_volume;
		this.a_goal = a_goal;
	}
}
