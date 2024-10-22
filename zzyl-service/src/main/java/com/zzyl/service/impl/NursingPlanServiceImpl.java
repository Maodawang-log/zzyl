package com.zzyl.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzyl.base.PageResponse;
import com.zzyl.dto.NursingPlanDto;
import com.zzyl.dto.NursingProjectPlanDto;
import com.zzyl.entity.NursingPlan;
import com.zzyl.entity.NursingProject;
import com.zzyl.entity.NursingProjectPlan;
import com.zzyl.mapper.NursingPlanMapper;
import com.zzyl.service.NursingPlanService;
import com.zzyl.vo.NursingPlanVo;
import com.zzyl.vo.NursingProjectVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NursingPlanServiceImpl implements NursingPlanService {

    @Autowired
    private NursingPlanMapper nursingPlanMapper;

    @Override
    public List<NursingPlanVo> getAllPlans() {
        List<NursingPlan> nursingPlans = nursingPlanMapper.getAll();
        return nursingPlans.stream().map(this::convertToVo).collect(Collectors.toList());
    }
    @Override
    @Transactional
    public void addPlan(NursingPlanDto nursingPlanDto) {
        if (nursingPlanDto.getStatus() == null) {
            // 设置默认状态
            nursingPlanDto.setStatus(1);
        }

        // 插入护理计划
        NursingPlan nursingPlan = convertToEntity(nursingPlanDto);
        nursingPlanMapper.insert(nursingPlan);

        // 插入护理计划中的各个项目
        List<NursingProjectPlanDto> projectPlans = nursingPlanDto.getProjectPlans();
        if (projectPlans != null && !projectPlans.isEmpty()) {
            for (NursingProjectPlanDto projectPlan : projectPlans) {
                NursingProjectPlan entity = convertToEntity(projectPlan);
                entity.setPlanId(nursingPlan.getId());
                nursingPlanMapper.insertProjectPlan(entity);
            }
        }
    }

    @Override
    public PageResponse<NursingPlanVo> searchPlans(String name, Integer status, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<NursingPlan> nursingPlans = nursingPlanMapper.search(name, status);
        PageResponse<NursingPlanVo> projectVoPageResponse = PageResponse.of(nursingPlans, NursingPlanVo.class);
        return  projectVoPageResponse;
    }
    @Override
    public NursingPlanVo getPlanById(Long id) {
        NursingPlan nursingPlan = nursingPlanMapper.getById(id);
        return convertToVo(nursingPlan);
    }

    @Override
    @Transactional
    public void updatePlan(NursingPlanDto nursingPlanDto) {
        // 第一步：更新护理计划表
        NursingPlan nursingPlan = convertToEntity(nursingPlanDto);
        nursingPlanMapper.update(nursingPlan);

        // 第二步：删除与该计划ID相关的所有护理项目计划数据，避免重复
        nursingPlanMapper.deleteProjectPlansByPlanId(nursingPlan.getId());

        // 第三步：插入新的护理项目计划数据
        List<NursingProjectPlanDto> projectPlans = nursingPlanDto.getProjectPlans();
        if (projectPlans != null && !projectPlans.isEmpty()) {
            for (NursingProjectPlanDto projectPlan : projectPlans) {
                NursingProjectPlan entity = convertToEntity(projectPlan);
                entity.setPlanId(nursingPlan.getId());
                nursingPlanMapper.insertProjectPlan(entity);
            }
        }
    }

    @Override
    public void updateStatus(Long id, Integer status) {
        NursingPlan nursingPlan = new NursingPlan();
        nursingPlan.setId(id);
        nursingPlan.setStatus(status);
        nursingPlanMapper.update(nursingPlan);
    }

    @Override
    public void deletePlan(Long id) {
        NursingPlan nursingPlan = nursingPlanMapper.getById(id);
        if (nursingPlan != null && nursingPlan.getStatus() == 0) {
            nursingPlanMapper.delete(id);
        } else {
            throw new IllegalStateException("只能删除禁用状态的护理计划");
        }
    }

    @Override
    public NursingPlanVo getNursingPlanById(Long id) {
        return nursingPlanMapper.getNursingPlanById(id);
    }

    //DTO转换成entity
    private NursingPlan convertToEntity(NursingPlanDto nursingPlanDto) {
        NursingPlan entity = new NursingPlan();
        BeanUtils.copyProperties(nursingPlanDto, entity);
        return entity;
    }
    private NursingProjectPlan convertToEntity(NursingProjectPlanDto projectPlanDto) {
        NursingProjectPlan entity = new NursingProjectPlan();
        BeanUtils.copyProperties(projectPlanDto, entity);
        return entity;
    }
    //entity转换成Vo
    private NursingPlanVo convertToVo(NursingPlan nursingPlan) {
        NursingPlanVo vo = new NursingPlanVo();
        BeanUtils.copyProperties(nursingPlan, vo);
        return vo;
    }

}