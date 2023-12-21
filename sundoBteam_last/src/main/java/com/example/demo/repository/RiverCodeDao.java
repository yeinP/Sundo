package com.example.demo.repository;

import com.example.demo.dto.riverCode.RiverCodeDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RiverCodeDao {

    @Select("select * from river_code")
    List<RiverCodeDto> riverCode();

    @Select("select * from river_code where r_code = #{r_code}")
    RiverCodeDto selectRcode(@Param("r_code") int r_code);

}
