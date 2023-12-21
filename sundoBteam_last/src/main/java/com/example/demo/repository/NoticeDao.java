package com.example.demo.repository;

import com.example.demo.dto.notice.NoticeDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoticeDao {

	@Insert("INSERT INTO notice(title, content, writer, reg_date) " +
			"VALUES (#{notice.title}, #{notice.content}, #{notice.writer}, now())")
	int insertOne(@Param("notice") NoticeDto noticeDto);

    @Select("select count(*) from notice")
    int count();
    
    @Select("select * from notice where notice_no = #{notice_no}")
    NoticeDto selectOne(int notice_no);

    @Select("select * from notice order by notice_no desc limit #{returnValue} offset #{value}")
    List<NoticeDto> selectAll(@Param("returnValue") int pageSize, @Param("value") int offset);

    @Update(" ")
    int updateViewCnt();
    
    @Update("UPDATE notice " +
    		"SET title = #{notice.title}, content = #{notice.content}, reg_date = now() " +
    		"WHERE notice_no = #{notice_no}")
    int updateOne(@Param("notice") NoticeDto noticeDto, @Param("notice_no") int notice_no);
    
    @Delete("DELETE FROM notice WHERE notice_no = #{notice_no}")
    int deleteOne(int notice_no);
    
    @Delete("truncate notice")
    int deleteAll();
    
    @Select("select count(*) from notice where title like '%' || #{searchKeyword} || '%'")
    int selectSimilarListCount(String searchKeyword);
    
    @Select("select * from notice where title like '%' || #{searchKeyword} || '%' order by notice_no desc limit #{returnValue} offset #{value}")
    List<NoticeDto> selectSimilarList(@Param("searchKeyword")String searchKeyword, @Param("returnValue") int pageSize, @Param("value") int offset);
    
    @Select("select * from notice where notice_no = #{searchKeyword}")
    NoticeDto selectSearchNotice(int searchKeyword);
}
