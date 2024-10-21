package com.zzyl.service.impl;

import com.zzyl.entity.NursingPlan;
import com.zzyl.mapper.NursingPlanMapper;
import com.zzyl.service.NursingPlanService;
import com.zzyl.vo.NursingPlanVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    private NursingPlanVo convertToVo(NursingPlan nursingPlan) {
        NursingPlanVo vo = new NursingPlanVo();
        BeanUtils.copyProperties(nursingPlan, vo);
        return vo;
    }
}