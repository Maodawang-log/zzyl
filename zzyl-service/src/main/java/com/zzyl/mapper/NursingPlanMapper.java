package com.zzyl.mapper;

import com.zzyl.entity.NursingPlan;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface NursingPlanMapper {

    @Select("SELECT * FROM nursing_plan")
    List<NursingPlan> getAll();
}