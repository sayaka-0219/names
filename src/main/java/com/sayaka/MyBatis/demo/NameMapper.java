package com.sayaka.MyBatis.demo;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;


@Mapper
public interface NameMapper {
        @Select("SELECT * FROM names WHERE name LIKE CONCAT (#{prefix}, '%') AND name LIKE CONCAT ('%', #{suffix})")
        List<Name> findByNameStartingWith(String prefix, String suffix);

        @Select("SELECT * FROM names WHERE id = #{id}")
        Optional<Name> findById(int id);

        @Insert("INSERT INTO names (name) VALUES (#{name})")
        @Options(useGeneratedKeys = true, keyProperty = "id")
        void insert(Name user);
}
