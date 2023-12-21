package com.example.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.demo.dto.complaint.C_userDto;
import com.example.demo.dto.complaint.ComplaintDto;
import com.example.demo.dto.complaint.R_fileDto;
import com.example.demo.dto.complaint.C_fileDto;
import com.example.demo.dto.complaint.Reply;


@Mapper
public interface ComplaintDao {

	@Select("Select count(*) from complaint")
	int selectCountAll();
	
	@Select("Select count(*) from complaint c, c_user u where c.cuno = u.cuno and u.cuname = #{param1} and u.cuphone = #{param2}")
	int selectCountMy(String cuname, String cuphone);
	
	@Select("SELECT COUNT(*) FROM complaint WHERE regdate BETWEEN to_timestamp(#{param1}, 'YYYY-MM-DD') AND (to_timestamp(#{param2}, 'YYYY-MM-DD') + interval '1 day')")
	int selectCountDate(String start, String end);

	@Select("SELECT COUNT(*) FROM complaint WHERE regdate BETWEEN to_timestamp(#{param1}, 'YYYY-MM-DD') AND (to_timestamp(#{param2}, 'YYYY-MM-DD') + interval '1 day') and ctitle ILIKE '%' || #{param3} || '%'")
	int selectCountDateKeyword(String start, String end, String keyword);
	
	@Select("WITH subquery AS (SELECT c.cno, c.c_loc, c.ctitle, c.c_content, c.cuno, u.cuname, c.c_count, c.secret, TO_CHAR(c.regdate,'YYYY-MM-DD HH24:MI') as regDate, "
			+ "c.status, ROW_NUMBER() OVER (ORDER BY c.regDate DESC) as row_num "
			+ "FROM complaint c, c_user u where c.cuno = u.cuno ) SELECT subquery.cno, subquery.c_loc, subquery.ctitle, subquery.c_content, "
			+ "subquery.cuno, subquery.cuname, subquery.c_count, subquery.secret, subquery.regdate, subquery.status, subquery.row_num FROM subquery WHERE subquery.row_num BETWEEN #{param1} AND #{param2}")
	List<ComplaintDto> selectAll(int startCno, int endCno);
	
	@Select("WITH subquery AS (SELECT c.cno, c.c_loc, c.ctitle, c.c_content, c.cuno, u.cuname, c.c_count, c.secret, TO_CHAR(c.regdate,'YYYY-MM-DD HH24:MI') as regDate, "
			+ "c.status, ROW_NUMBER() OVER (ORDER BY c.regDate DESC) as row_num "
			+ "FROM complaint c, c_user u where c.cuno = u.cuno and c.regdate BETWEEN to_timestamp(#{param1}, 'YYYY-MM-DD') AND (to_timestamp(#{param2}, 'YYYY-MM-DD') + interval '1 day')) SELECT subquery.cno, subquery.c_loc, subquery.ctitle, subquery.c_content, "
			+ "subquery.cuno, subquery.cuname, subquery.c_count, subquery.secret, subquery.regdate, subquery.status, subquery.row_num FROM subquery WHERE subquery.row_num BETWEEN #{param3} AND #{param4}")
	List<ComplaintDto> seacrhByDate(String start, String end, int startCno, int endCno);
	
	@Select("WITH subquery AS (SELECT c.cno, c.c_loc, c.ctitle, c.c_content, c.cuno, u.cuname, c.c_count, c.secret, TO_CHAR(c.regdate,'YYYY-MM-DD HH24:MI') as regDate, "
			+ "c.status, ROW_NUMBER() OVER (ORDER BY c.regDate DESC) as row_num "
			+ "FROM complaint c, c_user u where c.cuno = u.cuno and c.regdate BETWEEN to_timestamp(#{param1}, 'YYYY-MM-DD') AND (to_timestamp(#{param2}, 'YYYY-MM-DD') + interval '1 day') and c.ctitle ILIKE '%' || #{param3} || '%') SELECT subquery.cno, subquery.c_loc, subquery.ctitle, subquery.c_content, "
			+ "subquery.cuno, subquery.cuname, subquery.c_count, subquery.secret, subquery.regdate, subquery.status, subquery.row_num FROM subquery WHERE subquery.row_num BETWEEN #{param4} AND #{param5}")
	List<ComplaintDto> seacrhByDateKeyword(String start, String end, String keyword, int startCno, int endCno);
	
	@Select("select max(cuno) from c_user")
	int getCuno();
	
	@Select("select max(cno) from complaint")
	int getCno();
	
	@Select("select max(rno) from reply")
	int getRno();
	
	@Select("select cuphone from c_user where cuno = #{param1}")
	String getphone(int cuno);
	
	@Insert("insert into complaint(ctitle, c_content, cuno, secret, pword, regdate, c_loc) "
	 + "values (#{param1}, #{param2}, #{param3}, #{param4}, #{param5}, now(), #{param6})")
	void insertOne(String ctitle, String c_content, int cuno, int secret, String pword, String c_loc);

	@Insert("insert into c_user(cutype, cuname, cuphone, cugender, cubirth, cuaddress) "
	        + "values (#{param1}, #{param2}, #{param3}, #{param4}, #{param5}, #{param6})")
	void insertC_user(int cuType, String cuName, String cuPhone, int cuGender, String cuBirth, String cuAddress);

	@Insert("insert into c_file(cno, ogFile_name, file_name, file_path, file_size, created_date) values (#{cno}, #{ogFile_name}, #{file_name}, #{file_path}, #{file_size}, now())")
	void insertC_File(C_fileDto fileDto);
	
	@Select("SELECT c.cno, c.c_loc, c.ctitle, c.c_content, c.cuno, u.cuname, c.c_count, c.secret, c.status, "
			+ "TO_CHAR(c.regdate,'YYYY-MM-DD HH24:MI') as regDate, TO_CHAR(c.revdate,'YYYY-MM-DD HH24:MI') as revDate "
			+ "FROM complaint c JOIN c_user u ON c.cuno = u.cuno and c.cno = #{param1}")
	ComplaintDto readOneCom(int cno);
	
	@Select("SELECT fno, cno, OGFILE_NAME, FILE_NAME, FILE_PATH, round((FILE_SIZE /1024)) as file_size FROM c_FILE WHERE cNO = #{cno}")
    List<C_fileDto> selectC_file(int fno);
	
    @Select("SELECT fno, OGFILE_NAME, FILE_NAME, FILE_PATH, FILE_SIZE FROM c_FILE WHERE fNO = #{fno}")
    C_fileDto selectOneCFile(int fno);
    
    @Select("SELECT fno, OGFILE_NAME, FILE_NAME, FILE_PATH, FILE_SIZE FROM r_FILE WHERE fNO = #{fno}")
    R_fileDto selectOneRFile(int fno);
    
    @Update("update complaint set c_count = c_count + 1 where cno =  #{param1}")
    void updateCount(int cno);
    
	@Select("SELECT pword from complaint WHERE cNO = #{cno}")
    String getPword(int cno);
	
    @Update("update complaint set ctitle = #{ctitle}, c_loc = #{c_loc}, c_content = #{c_content}, revdate = now() where cno = #{cno}")
    void updateCom(ComplaintDto complaintDto);
    
    @Delete("delete from complaint where cno = #{param1}")
    void deleteCom(int cno);
    
    @Insert("insert into reply(cno, rtitle, rcontent, regdate) values (#{param1}, #{param2}, #{param3}, now())")
    void insertReply(int cno, String rtitle, String rcontent);
    
    @Insert("insert into r_file(rno, ogFile_name, file_name, file_path, file_size, created_date) values (#{rno}, #{ogFile_name}, #{file_name}, #{file_path}, #{file_size}, now())")
	void insertR_File(R_fileDto fileDto);
    
    @Update("update complaint set status = #{param1} where cno = #{param2}")
    void updateStatus(int status, int cno);
    
    @Select("Select * from reply where cno = #{param1}")
    Reply showRep(int cno);
    
    @Select("SELECT fno, rno, OGFILE_NAME, FILE_NAME, FILE_PATH, round((FILE_SIZE /1024)) as file_size FROM r_FILE WHERE rNO = #{rno}")
    List<R_fileDto> selectR_file(int rno);

	@Select("WITH subquery AS (SELECT c.cno, c.c_loc, c.ctitle, c.c_content, u.cuphone, u.cuname, c.c_count, c.secret, TO_CHAR(c.regdate,'YYYY-MM-DD HH24:MI') as regDate, \r\n"
			+ "c.status, ROW_NUMBER() OVER (ORDER BY c.regDate DESC) as row_num FROM complaint c, c_user u where c.cuno = u.cuno and u.cuname = #{param1} and u.cuphone = #{param2})"
			+ "SELECT subquery.cno, subquery.c_loc, subquery.ctitle, subquery.c_content,"
			+ "subquery.cuphone, subquery.cuname, subquery.c_count, subquery.secret, subquery.regdate, subquery.status, subquery.row_num FROM subquery WHERE subquery.row_num BETWEEN #{param3} AND #{param4}")
	List<ComplaintDto> selectMyCom(String cuname, String cuphone, int startRow, int endRow);
	

}