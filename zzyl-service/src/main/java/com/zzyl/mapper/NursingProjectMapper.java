package com.zzyl.mapper;

import com.github.pagehelper.Page;
import com.zzyl.entity.NursingProject;
import com.zzyl.vo.NursingProjectVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 护理项目Mapper接口
 */
@Mapper
public interface NursingProjectMapper {

    Page<NursingProject> selectByPage(String name, Integer status);

    void insert(NursingProject nursingProject);

    NursingProject selectById(Long id);

    void update(NursingProject nursingProject);

    void del(Long id);

    List<NursingProject> getALL();
}