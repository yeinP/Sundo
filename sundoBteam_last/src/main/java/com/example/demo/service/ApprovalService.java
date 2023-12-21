package com.example.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.approval.A_fileDto;
import com.example.demo.dto.approval.ApprovalDto;
import com.example.demo.dto.complaint.C_fileDto;
import com.example.demo.dto.complaint.ComplaintDto;
import com.example.demo.dto.complaint.Reply;
import com.example.demo.repository.ApprovalDao;

@Service
public class ApprovalService {

	@Autowired
	ApprovalDao approvalDao;
	
	public int InsertApp(ApprovalDto approvalDto) {
		approvalDao.InsertApproval(approvalDto);
		return approvalDao.selectRecApp();
	}
	
	public void uploadA_file(A_fileDto fileDto) {
		approvalDao.insertA_File(fileDto);
	}
	
	public A_fileDto download_file(int fno) {
		return approvalDao.selectOneFile(fno);
	}
	
	public int countAllApp() {
		return approvalDao.selectCountAll();
	}
	
	public int countInbox() {
		return approvalDao.selectCountInbox();
	}
	
	public int countDateApp(String start, String end) {
		return approvalDao.selectCountDate(start, end);
	}
	
	public int countDateR_codeApp(String start, String end, int r_code) {
		return approvalDao.selectCountDateR_code(start, end, r_code);
	}
	
	public List<ApprovalDto> showList(int startCno, int endCno){
		return approvalDao.selectAll(startCno, endCno);
	};
	
	public List<ApprovalDto> showInbox(int startCno, int endCno){
		return approvalDao.selectInbox(startCno, endCno);
	};
	
	public List<ApprovalDto> searchDate(int StartCno, int endCno, String start, String end){
		return approvalDao.seacrhByDate(start, end, StartCno, endCno);
	}
	
	public List<ApprovalDto> searchDateR_code(int StartCno, int endCno, int r_code, String start, String end){
		return approvalDao.seacrhByDateR_code(start, end, r_code, StartCno, endCno);
	}
	
	public Map<Integer, Object> readApp(int a_no) {
		Map<Integer, Object> map = new HashMap<>();
		map.put(1, approvalDao.readOneApp(a_no));
		map.put(2, approvalDao.selectA_file(a_no));
		return map;
	}
	
	public A_fileDto downloadA_file(int fno) {
		return approvalDao.selectOneAFile(fno);
	}
	
	public void updateStatus(int a_no, int a_status) {
		approvalDao.updateStatus(a_status, a_no);
		if(a_status == 3 || a_status ==4) {
		approvalDao.updateAppDate(a_no);
		}
	}
	
	public void approve(int a_no, int a_status, String a_comment) {
		approvalDao.updateStatus(a_status, a_no);
		approvalDao.InsertComment(a_comment, a_no);
		approvalDao.updateAppDate(a_no);
	}
	
	public List<ApprovalDto> usageList(int r_code){
		return approvalDao.seacrhByR_code(r_code);
	};
	
	public String getPhoneNum(int a_no) {
		return approvalDao.getphone(a_no);
	}
	
	public List<ApprovalDto> searchMyApp(String a_name, String a_phone){
		return approvalDao.seacrhByUser(a_name, a_phone);
	}
}
