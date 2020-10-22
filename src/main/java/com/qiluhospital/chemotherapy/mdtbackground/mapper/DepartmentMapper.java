package com.qiluhospital.chemotherapy.mdtbackground.mapper;

import com.qiluhospital.chemotherapy.mdtbackground.entity.Department;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface DepartmentMapper {
    @Select("select * from mdt_department")
    List<Department> findAll();

    @Insert("insert into mdt_department(depart_name) values(#{departmentName})")
    Boolean AddDepartment(String departmentName);

    @Select("select * from mdt_department where id = #{id}")
    Department findById(Long id);

    @Update("update mdt_department set depart_name=#{name} where id = #{id}")
    Boolean updateById(Long id, String name);

    @Select("select depart_name from mdt_department where id =#{id}")
    String findDepartmentNameById(Long id);
}
