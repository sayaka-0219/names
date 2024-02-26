package com.sayaka.MyBatis.demo;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface NameMapper {
        @Select("SELECT * FROM names WHERE name LIKE CONCAT (#{prefix}, '%') AND name LIKE CONCAT ('%', #{suffix})")
        List<Name> findByNameStartingWith(String prefix, String suffix);
}
