package com.sayaka.MyBatis.demo.dao;

import com.sayaka.MyBatis.demo.entity.Name;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;


@Mapper
public interface NameMapper {

        //文字検索でのRead処理
        @Select("SELECT * FROM names WHERE name LIKE CONCAT (#{prefix}, '%') AND name LIKE CONCAT ('%', #{suffix})")
        List<Name> findByNameStartingWith(String prefix, String suffix);

        //ID検索でのRead処理
        @Select("SELECT * FROM names WHERE id = #{id}")
        Optional<Name> findById(int id);

        //Create処理
        @Insert("INSERT INTO names (name) VALUES (#{name})")
        @Options(useGeneratedKeys = true, keyProperty = "id")
        void insert(Name user);

}
