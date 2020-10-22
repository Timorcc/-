package com.qiluhospital.chemotherapy.mdtbackground.mapper;

import com.qiluhospital.chemotherapy.mdtbackground.entity.SmallSecretary;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SmallSecretaryMapper {
    @Select("select * from mdt_small_secretary")
    List<SmallSecretary> findAll();

    @Select("select * from mdt_small_secretary where tel_num = #{telNum}")
    SmallSecretary findByTelNum(String telNum);

    @Insert("insert into mdt_small_secretary(username,tel_num,wx_num) values(#{username},#{telNum},#{wxNum})")
    Boolean AddSmallSecretary(String username, String telNum, String wxNum);

    @Select("select * from mdt_small_secretary where id = #{id}")
    SmallSecretary findById(Long id);

    @Update("update mdt_small_secretary set username=#{username},tel_num=#{telNum},wx_num=#{wxNum} where id=#{id}")
    Boolean updateById(Long id, String username, String telNum, String wxNum);

    @Select("select * from mdt_small_secretary where username like '%${name}%'  ")
    List<SmallSecretary> fuzzyFind(@Param("name") String name);


}
