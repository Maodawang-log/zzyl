package com.zzyl.service;

import com.github.pagehelper.PageInfo;
import com.zzyl.base.PageResponse;
import com.zzyl.dto.NursingPlanDto;
import com.zzyl.vo.NursingPlanVo;

import java.util.List;

/**
 * @description:
 * @author: 李研
 * @date: 2024/10/21
 */

public interface NursingPlanService {
    List<NursingPlanVo> getAllPlans();

    void addPlan(NursingPlanDto nursingPlanDto);

    PageResponse<NursingPlanVo> searchPlans(String name, Integer status, Integer pageNum, Integer pageSize);

    NursingPlanVo getPlanById(Long id);

    void updatePlan(NursingPlanDto nursingPlanDto);

    void updateStatus(Long id, Integer status);

    void deletePlan(Long id);

    NursingPlanVo getNursingPlanById(Long id);
}
