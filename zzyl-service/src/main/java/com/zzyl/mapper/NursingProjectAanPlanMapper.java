package com.zzyl.mapper;

import com.zzyl.entity.NursingProjectPlan;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @description:
 * @author: 李研
 * @date: 2024/10/22
 */
@Mapper
public interface NursingProjectAanPlanMapper {
    @Select("select * from nursing_project_plan where plan_id = #{id}")
    List<NursingProjectPlan> planById(long id);

    @Select("select * from nursing_project_plan where project_id = #{id}")
    List<NursingProjectPlan> ProjectById(Long id);
}
