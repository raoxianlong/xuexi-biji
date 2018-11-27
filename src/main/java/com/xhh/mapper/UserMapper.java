package com.xhh.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {

    @Select("SELECT * FROM `user` where name=#{name}")
    public UserEntity findUserByName(@Param("name") String name);


}
