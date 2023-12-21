package com.example.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.demo.dto.approval.A_fileDto;
import com.example.demo.dto.approval.ApprovalDto;
import com.example.demo.dto.complaint.C_fileDto;
import com.example.demo.dto.complaint.ComplaintDto;


@Mapper
public interface ApprovalDao {

	@Insert("Insert into approval (a_status, r_code, a_name, a_phone, a_place, a_purpose, startDate, endDate, a_volume, a_goal, regdate) "
			+ "values (#{a_status}, #{r_code}, #{a_name}, #{a_phone}, #{a_place}, #{a_purpose}, to_date(#{startDate},'YYYY-MM-DD'), to_date(#{endDate},'YYYY-MM-DD'), #{a_volume}, #{a_goal}, now())")
	public void InsertApproval(ApprovalDto approvalDto);
	
	@Select("select max(a_no) from approval")
	public int selectRecApp();
	
	@Insert("insert into a_file(a_no, ogFile_name, file_name, file_path, file_size, created_date) values (#{a_no}, #{ogFile_name}, #{file_name}, #{file_path}, #{file_size}, now())")
	void insertA_File(A_fileDto fileDto);
	
    @Select("SELECT fno, OGFILE_NAME, FILE_NAME, FILE_PATH, FILE_SIZE FROM A_FILE WHERE fNO = #{fno}")
    A_fileDto selectOneFile(int fno);
    
	@Select("Select count(*) from approval")
	int selectCountAll();
	
	@Select("Select count(*) from approval where a_status = 2")
	int selectCountInbox();
	
	@Select("SELECT COUNT(*) FROM approval WHERE regdate BETWEEN to_timestamp(#{param1}, 'YYYY-MM-DD') AND (to_timestamp(#{param2}, 'YYYY-MM-DD') + interval '1 day')")
	int selectCountDate(String start, String end);
	
	@Select("SELECT COUNT(*) FROM approval WHERE regdate BETWEEN to_timestamp(#{param1}, 'YYYY-MM-DD') AND (to_timestamp(#{param2}, 'YYYY-MM-DD') + interval '1 day') and r_code = #{param3}")
	int selectCountDateR_code(String start, String end, int r_code);
	
	@Select("WITH subquery AS (SELECT a.a_no, a.a_status, r.r_code, r.r_name, a.a_name, a.a_phone, a.a_place, a.a_purpose, "
			+ "TO_CHAR(a.startDate,'YYYY-MM-DD') as startDate, TO_CHAR(a.endDate,'YYYY-MM-DD') as endDate, "
			+ "a.a_volume, a.a_goal, TO_CHAR(a.regDate,'YYYY-MM-DD') as regDate, "
			+ "TO_CHAR(a.appDate,'YYYY-MM-DD') as appDate, ROW_NUMBER() OVER (ORDER BY a.regDate DESC) as row_num "
			+ "FROM approval a, river_code r where r.r_code = a.r_code) SELECT subquery.a_no, subquery.a_status, subquery.r_name, subquery.r_code, "
			+ "subquery.a_name, subquery.a_phone, subquery.a_place, subquery.a_purpose, subquery.startDate, subquery.endDate, "
			+ "subquery.a_volume, subquery.a_goal, subquery.regDate, subquery.appDate FROM subquery WHERE subquery.row_num BETWEEN #{param1} AND #{param2}")
	List<ApprovalDto> selectAll(int startCno, int endCno);
	
	@Select("WITH subquery AS (SELECT a.a_no, a.a_status, r.r_code, r.r_name, a.a_name, a.a_phone, a.a_place, a.a_purpose, "
			+ "TO_CHAR(a.startDate,'YYYY-MM-DD') as startDate, TO_CHAR(a.endDate,'YYYY-MM-DD') as endDate, "
			+ "a.a_volume, a.a_goal, TO_CHAR(a.regDate,'YYYY-MM-DD') as regDate, "
			+ "TO_CHAR(a.appDate,'YYYY-MM-DD') as appDate, ROW_NUMBER() OVER (ORDER BY a.regDate DESC) as row_num "
			+ "FROM approval a, river_code r where r.r_code = a.r_code and a.a_status = 2) SELECT subquery.a_no, subquery.a_status, subquery.r_name, subquery.r_code, "
			+ "subquery.a_name, subquery.a_phone, subquery.a_place, subquery.a_purpose, subquery.startDate, subquery.endDate, "
			+ "subquery.a_volume, subquery.a_goal, subquery.regDate, subquery.appDate FROM subquery WHERE subquery.row_num BETWEEN #{param1} AND #{param2}")
	List<ApprovalDto> selectInbox(int startCno, int endCno);
	
	
	@Select("WITH subquery AS (SELECT a.a_no, a.a_status, r.r_code, r.r_name, a.a_name, a.a_phone, a.a_place, a.a_purpose, "
			+ "TO_CHAR(a.startDate,'YYYY-MM-DD') as startDate, TO_CHAR(a.endDate,'YYYY-MM-DD') as endDate, "
			+ "a.a_volume, a.a_goal, TO_CHAR(a.regDate,'YYYY-MM-DD') as regDate, "
			+ "TO_CHAR(a.appDate,'YYYY-MM-DD') as appDate, ROW_NUMBER() OVER (ORDER BY a.regDate DESC) as row_num "
			+ "FROM approval a, river_code r where r.r_code = a.r_code and a.regdate BETWEEN to_timestamp(#{param1}, 'YYYY-MM-DD') "
			+ "AND (to_timestamp(#{param2}, 'YYYY-MM-DD') + interval '1 day')) SELECT subquery.a_no, subquery.a_status, subquery.r_name, subquery.r_code, "
			+ "subquery.a_name, subquery.a_phone, subquery.a_place, subquery.a_purpose, subquery.startDate, subquery.endDate, "
			+ "subquery.a_volume, subquery.a_goal, subquery.regDate, subquery.appDate FROM subquery WHERE subquery.row_num BETWEEN #{param3} AND #{param4}")
	List<ApprovalDto> seacrhByDate(String start, String end, int startCno, int endCno);
	
	@Select("WITH subquery AS (SELECT a.a_no, a.a_status, r.r_code, r.r_name, a.a_name, a.a_phone, a.a_place, a.a_purpose, "
			+ "TO_CHAR(a.startDate,'YYYY-MM-DD') as startDate, TO_CHAR(a.endDate,'YYYY-MM-DD') as endDate, "
			+ "a.a_volume, a.a_goal, TO_CHAR(a.regDate,'YYYY-MM-DD') as regDate, "
			+ "TO_CHAR(a.appDate,'YYYY-MM-DD') as appDate, ROW_NUMBER() OVER (ORDER BY a.regDate DESC) as row_num "
			+ "FROM approval a, river_code r where r.r_code = a.r_code and a.regdate BETWEEN to_timestamp(#{param1}, 'YYYY-MM-DD') "
			+ "AND (to_timestamp(#{param2}, 'YYYY-MM-DD') + interval '1 day') and r.r_code = #{param3}) SELECT subquery.a_no, subquery.a_status, subquery.r_name, subquery.r_code, "
			+ "subquery.a_name, subquery.a_phone, subquery.a_place, subquery.a_purpose, subquery.startDate, subquery.endDate, "
			+ "subquery.a_volume, subquery.a_goal, subquery.regDate, subquery.appDate FROM subquery WHERE subquery.row_num BETWEEN #{param4} AND #{param5}")
	List<ApprovalDto> seacrhByDateR_code(String start, String end, int r_code, int startCno, int endCno);
	
	@Select("SELECT a.a_no, a.a_status, r.r_code, r.r_name, a.a_name, a.a_phone, a.a_place, a.a_purpose, a.a_volume, a.a_goal, "
			+ "TO_CHAR(a.regDate,'YYYY-MM-DD') as regDate, TO_CHAR(a.appDate,'YYYY-MM-DD') as appDate, "
			+ "TO_CHAR(a.startDate,'YYYY-MM-DD') as startDate, TO_CHAR(a.endDate,'YYYY-MM-DD') as endDate, a.a_comment "
			+ "FROM approval a JOIN river_code r ON a.r_code = r.r_code and a.a_no = #{param1}")
	ApprovalDto readOneApp(int a_no);
	
	@Select("SELECT fno, a_no, OGFILE_NAME, FILE_NAME, FILE_PATH, round((FILE_SIZE /1024)) as file_size FROM A_FILE WHERE a_no = #{param1}")
    List<A_fileDto> selectA_file(int a_no);

    @Select("SELECT fno, OGFILE_NAME, FILE_NAME, FILE_PATH, FILE_SIZE FROM A_FILE WHERE fNO = #{fno}")
    A_fileDto selectOneAFile(int fno);
    
    @Update("update approval set a_status = #{param1} where a_no = #{param2}")
    void updateStatus(int a_status, int a_no);
    
    @Update("update approval set a_comment = #{param1} where a_no = #{param2}")
    void InsertComment(String a_comment, int a_no);
    
    @Update("update approval set appDate = now() where a_no = #{param2}")
    void updateAppDate(int a_no);
    
    @Select("SELECT a_no, r_code, a_name, (SUBSTRING(a_phone FROM 1 FOR 3) || '-' || SUBSTRING(a_phone FROM 4 FOR 4) || '-' || SUBSTRING(a_phone FROM 8)) as a_phone, a_place, a_purpose, "
    		+ "TO_CHAR(startDate,'YYYY-MM-DD') as startDate, TO_CHAR(endDate,'YYYY-MM-DD') as endDate, "
    		+ "a_volume, a_goal FROM approval where startDate <= now() and endDate >= now() "
    		+ "and r_code = #{param1} and a_status = 3")
	List<ApprovalDto> seacrhByR_code(int r_code);
    
	@Select("select a_phone from approval where a_no = #{param1}")
	String getphone(int a_no);
	
	@Select("SELECT a.a_no, a.a_status, r.r_code, r.r_name, a.a_name, a.a_phone, a.a_place, a.a_purpose, "
			+ "TO_CHAR(a.startDate,'YYYY-MM-DD') as startDate, TO_CHAR(a.endDate,'YYYY-MM-DD') as endDate, "
			+ "a.a_volume, a.a_goal, TO_CHAR(a.regDate,'YYYY-MM-DD') as regDate, "
			+ "TO_CHAR(a.appDate,'YYYY-MM-DD') as appDate FROM approval a, river_code r where r.r_code = a.r_code and a.a_name = #{param1} "
			+ "and a.a_phone = #{param2}")
	List<ApprovalDto> seacrhByUser(String a_name, String a_phone);
}