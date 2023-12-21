package com.example.demo.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.complaint.C_userDto;
import com.example.demo.dto.complaint.ComplaintDto;
import com.example.demo.dto.complaint.R_fileDto;
import com.example.demo.dto.complaint.Reply;
import com.example.demo.dto.approval.ApprovalDto;
import com.example.demo.dto.complaint.C_fileDto;
import com.example.demo.repository.ComplaintDao;

@Service
public class ComplaintServiceImpl {

	@Autowired
	ComplaintDao complaintDao;
	
	public int countAllCom() {
		return complaintDao.selectCountAll();
	}
	
	public int countMyCom(String cuname, String cuphone) {
		return complaintDao.selectCountMy(cuname, cuphone);
	}
	
	public int countDateCom(String start, String end) {
		return complaintDao.selectCountDate(start, end);
	}
	
	public int countDateKeywordCom(String start, String end, String keyword) {
		return complaintDao.selectCountDateKeyword(start, end, keyword);
	}
	
	public List<ComplaintDto> showList(int startCno, int endCno){
		return complaintDao.selectAll(startCno, endCno);
	};
	
	public List<ComplaintDto> searchDate(int StartCno, int endCno, String start, String end){
		return complaintDao.seacrhByDate(start, end, StartCno, endCno);
	}
	
	public List<ComplaintDto> searchDateKeyword(int StartCno, int endCno, String start, String end, String keyword){
		return complaintDao.seacrhByDateKeyword(start, end, keyword, StartCno, endCno);
	}
	
	public int InsertC_user(C_userDto c_userDto) {
		complaintDao.insertC_user(c_userDto.getCuType(), c_userDto.getCuName(), c_userDto.getCuPhone(),
				c_userDto.getCuGender(), c_userDto.getCuBirth(), c_userDto.getCuAddress());
		return complaintDao.getCuno();
	}
	
	public int InsertOneCom(ComplaintDto complaintDto) {
		complaintDao.insertOne(complaintDto.getCtitle(),complaintDto.getC_content(),
				complaintDto.getCuno(), complaintDto.getSecret(), complaintDto.getPword(), complaintDto.getC_loc());
		return complaintDao.getCno();
	}
	
	public void uploadC_file(C_fileDto fileDto) {
		complaintDao.insertC_File(fileDto);
	}
	
	public void uploadR_file(R_fileDto fileDto) {
		complaintDao.insertR_File(fileDto);
	}
	
	public C_fileDto downloadC_file(int fno) {
		return complaintDao.selectOneCFile(fno);
	}
	
	public R_fileDto downloadR_file(int fno) {
		return complaintDao.selectOneRFile(fno);
	}
	
	public Map<Integer, Object> readCom(int cno) {
		Map<Integer, Object> map = new HashMap<>();
		Reply reply = complaintDao.showRep(cno);
		map.put(1, complaintDao.readOneCom(cno));
		map.put(2, complaintDao.selectC_file(cno));
		if (reply != null) {
			map.put(3, reply);
		    try {
		        Object rFile = complaintDao.selectR_file(reply.getRno());
		        if (rFile != null) {
		            map.put(4, rFile);
		        }
		    } catch (NullPointerException e) {
		        e.printStackTrace(); 
		    }
		}
		complaintDao.updateCount(cno);
		return map;
	}
	
	public String revDelCheck(int cno) {
		return complaintDao.getPword(cno);
	}
	
	public void reviseCom(ComplaintDto complaintDto) {
		complaintDao.updateCom(complaintDto);
	}
	
	public void deleteCom(int cno) {
		complaintDao.deleteCom(cno);
	}
	
	public int InsertOneRep(Reply reply) {
		complaintDao.insertReply(reply.getCno(), reply.getRtitle(), reply.getRcontent());
		return complaintDao.getRno();
	}
	
	public String getPhoneNum(int cuno) {
		return complaintDao.getphone(cuno);
	}
	
	public void updateStatus(int status, int cno) {
		complaintDao.updateStatus(status, cno);
	}
	
	public List<ComplaintDto> searchMyCom(String cuname, String cuphone, int startRow, int endRow){
		return complaintDao.selectMyCom(cuname, cuphone, startRow, endRow);
	}
}
