package com.example.demo.repository;

import com.example.demo.dto.account.UserDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserDao {
	
	@Select("select count(*) from users")
	int count();

	@Select("select role from users where id = #{id}")
	String selectUserRole(String id);

	@Select("select * from users where id = #{id}")
	UserDto selectOne(String id);

	@Select("select count(*) from users where id = #{id}")
	Integer isUser(String id);

	@Insert("INSERT INTO users(id, password, name, phone, reg_date) " +
			"VALUES (#{user.id}, #{user.password}, #{user.name}, #{user.phone}, now())")
	int insertUser(@Param("user") UserDto userDto);

}
