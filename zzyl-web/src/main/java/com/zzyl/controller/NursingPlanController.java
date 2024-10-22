package com.zzyl.controller;

import com.github.pagehelper.PageInfo;
import com.zzyl.base.PageResponse;
import com.zzyl.base.ResponseResult;
import com.zzyl.dto.NursingPlanDto;
import com.zzyl.service.NursingPlanService;
import com.zzyl.vo.NursingPlanVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/nursing")
@Api(tags = "护理计划管理")
public class NursingPlanController {

    @Autowired
    private NursingPlanService nursingPlanService;

    @GetMapping("/plan")
    @ApiOperation("查询所有护理计划")
    public ResponseResult<List<NursingPlanVo>> getAllNursingPlans() {
        List<NursingPlanVo> planList = nursingPlanService.getAllPlans();
        return ResponseResult.success(planList);
    }

    @PostMapping("/plan")
    @ApiOperation("新增护理计划")
    public ResponseResult<NursingPlanVo> addNursingPlan(@RequestBody NursingPlanDto nursingPlanDto) {
        nursingPlanService.addPlan(nursingPlanDto);
        return ResponseResult.success();
    }
    @GetMapping("/plan/search")
    @ApiOperation("分页查询护理计划")
    public ResponseResult<PageInfo<NursingPlanVo>> searchNursingPlans(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) Integer status) {
        PageResponse<NursingPlanVo> pageInfo = nursingPlanService.searchPlans(name, status, pageNum, pageSize);
        return ResponseResult.success(pageInfo);
    }

    @GetMapping("/plan/{id}")
    @ApiOperation("根据ID查询")
    public ResponseResult<NursingPlanVo> getNursingPlanById(@PathVariable Long id) {
        NursingPlanVo nursingPlanVo = nursingPlanService.getNursingPlanById(id);
        if (nursingPlanVo != null) {
            return ResponseResult.success(nursingPlanVo);
        } else {
            return ResponseResult.error("未找到对应的护理计划");
        }
    }
    @ApiOperation("修改数据")
    @PutMapping("/plan/{id}")
    public ResponseResult updateNursingPlan(@PathVariable Long id, @RequestBody NursingPlanDto nursingPlanDto) {
        nursingPlanDto.setId(id);
        nursingPlanService.updatePlan(nursingPlanDto);
        return ResponseResult.success();
    }

    @PutMapping("/{id}/status/{status}")
    @ApiOperation("禁用或开启状态")
    public ResponseResult<Void> updateStatus(@PathVariable Long id, @PathVariable Integer status) {
        nursingPlanService.updateStatus(id, status);
        return ResponseResult.success();
    }
    @DeleteMapping("/plan/{id}")
    @ApiOperation("删除数据")
    public ResponseResult<Void> deleteNursingPlan(@PathVariable Long id) {
        nursingPlanService.deletePlan(id);
        return ResponseResult.success();
    }

}