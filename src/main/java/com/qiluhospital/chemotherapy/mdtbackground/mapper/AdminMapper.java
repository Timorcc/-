package com.qiluhospital.chemotherapy.mdtbackground.mapper;

import com.qiluhospital.chemotherapy.mdtbackground.entity.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AdminMapper {
    @Select("select * from mdt_admin where username =#{username}")
    Admin findByUsername(String username);
}
