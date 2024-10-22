package com.zzyl.mapper;

import com.github.pagehelper.Page;
import com.zzyl.entity.NursingPlan;
import com.zzyl.entity.NursingProject;
import com.zzyl.entity.NursingProjectPlan;
import com.zzyl.vo.NursingPlanVo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NursingPlanMapper {

    @Select("SELECT * FROM nursing_plan")
    List<NursingPlan> getAll();

    void insert(NursingPlan nursingPlan);

    void insertProjectPlan(NursingProjectPlan nursingProjectPlan);


    Page<NursingPlan> search(@Param("name") String name, @Param("status") Integer status);

    @Select("SELECT * FROM nursing_plan WHERE id = #{id}")
    NursingPlan getById(@Param("id") Long id);

    void update(NursingPlan nursingPlan);

    // 删除与护理计划ID相关的所有项目计划
    @Delete("DELETE FROM nursing_project_plan WHERE plan_id = #{planId}")
    void deleteProjectPlansByPlanId(Long planId);

    void delete(Long id);

    NursingPlanVo getNursingPlanById(Long id);
}