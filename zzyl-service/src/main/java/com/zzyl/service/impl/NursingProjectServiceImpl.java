package com.zzyl.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zzyl.base.PageResponse;
import com.zzyl.dto.NursingProjectDto;
import com.zzyl.entity.NursingProject;
import com.zzyl.mapper.NursingProjectMapper;
import com.zzyl.service.NursingProjectService;
import com.zzyl.vo.NursingProjectVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 护理项目Service实现类
 */
@Service
public class NursingProjectServiceImpl implements NursingProjectService {

    @Autowired
    private NursingProjectMapper nursingProjectMapper;

    @Override
    public PageResponse<NursingProjectVo> getByPage(String name, Integer status, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<NursingProject> nursingProjects = nursingProjectMapper.selectByPage(name, status);
        PageResponse<NursingProjectVo> projectVoPageResponse = PageResponse.of(nursingProjects, NursingProjectVo.class);
        return projectVoPageResponse;
    }

    /**
     * 新增护理项目
     * @param nursingProjectDTO 护理项目数据传输对象
     */
    @Override
    public void add(NursingProjectDto nursingProjectDTO) {
        NursingProject nursingProject = new NursingProject();
        BeanUtils.copyProperties(nursingProjectDTO, nursingProject);
        nursingProjectMapper.insert(nursingProject);
    }

    /**
     * 根据ID查询护理项目
     * @param id
     * @return
     */
    @Override
    public NursingProjectVo getById(Long id) {
        NursingProject nursingProject = nursingProjectMapper.selectById(id);
        return BeanUtil.toBean(nursingProject,NursingProjectVo.class);
    }

    /**
     * 更新护理项目信息
     *
     * @param nursingProjectDTO 护理项目数据传输对象
     */
    @Override
    public void update(NursingProjectDto nursingProjectDTO) {
        NursingProject nursingProject = new NursingProject();
        BeanUtils.copyProperties(nursingProjectDTO, nursingProject);
        nursingProjectMapper.update(nursingProject);
    }

    @Override
    public void of(Long id, int status) {
        NursingProject nursingProject = new NursingProject();
        nursingProject.setId(id);
        nursingProject.setStatus(status);
        nursingProjectMapper.update(nursingProject);
    }

    @Override
    public void del(Long id) {
        nursingProjectMapper.del(id);
    }

    @Override
    public List<NursingProjectVo> getAllProjects() {
        List<NursingProject> nursingProjects = nursingProjectMapper.getALL();
        List<NursingProjectVo> nursingProjectVos = nursingProjects.stream().map(nursingProject -> {
            NursingProjectVo vo = new NursingProjectVo();
            BeanUtils.copyProperties(nursingProject, vo);
            return vo;
        }).collect(Collectors.toList());
        return nursingProjectVos;
    }
}