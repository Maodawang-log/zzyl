package com.zzyl.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @description:
 * @author: 李研
 * @date: 2024/10/22
 */
@Mapper
public interface NursingProjectAanPlanMapper {
    @Select("select * from nursing_project_plan where id = #{id}")
    Object projectByid(long id);
}
