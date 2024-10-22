package com.zzyl.mapper;

import com.github.pagehelper.Page;
import com.zzyl.entity.NursingLevel;
import com.zzyl.vo.NursingLevelVo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface NursingLevelMapper {

    @Select("SELECT * FROM nursing_level")
    List<NursingLevel> listAll();

    Page<NursingLevel> listByCondition(@Param("name") String name, @Param("status") Integer status);

    void insert(NursingLevel nursingLevel);

    NursingLevelVo getNursingLevelById(Long id);

    void updateNursingLevel(NursingLevel nursingLevel);

    @Delete("delete from nursing_level where id = #{id}")
    void deleteNursingLevel(Long id);

    List<NursingLevel> getByPlanId(Long id);
}
