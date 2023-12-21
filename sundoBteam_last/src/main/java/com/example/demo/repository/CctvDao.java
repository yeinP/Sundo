package com.example.demo.repository;

import com.example.demo.dto.cctvAddr.CctvAddr;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CctvDao {

    @Select("Select name,addr,code from Cctv")
    List<CctvAddr> cctv();

}
