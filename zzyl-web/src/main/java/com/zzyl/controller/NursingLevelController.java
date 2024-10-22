package com.zzyl.controller;

import com.github.pagehelper.Page;
import com.zzyl.base.PageResponse;
import com.zzyl.base.ResponseResult;
import com.zzyl.dto.NursingLevelDto;
import com.zzyl.service.NursingLevelService;
import com.zzyl.vo.NursingLevelVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/nursingLevel")
@Api("护理等级管理")
public class NursingLevelController {

    @Autowired
    private NursingLevelService nursingLevelService;

    @GetMapping("/listAll")
    @ApiOperation("查询所有")
    public ResponseResult<List<NursingLevelVo>> listAllNursingLevels() {
        List<NursingLevelVo> levels = nursingLevelService.getAllLevels();
        return ResponseResult.success(levels);
    }
    @GetMapping("/listByPage")
    @ApiOperation("分页查询")
    public ResponseResult<Page<NursingLevelVo>> listByPage(
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "status", required = false) Integer status) {

        PageResponse<NursingLevelVo> page = nursingLevelService.getLevelsByPage(pageNum, pageSize, name, status);
        return ResponseResult.success(page);
    }

    @PostMapping("/insert")
    @ApiOperation("新增护理等级")
    public ResponseResult insertNursingLevel(@RequestBody NursingLevelDto nursingLevelDto) {
        nursingLevelService.insertNursingLevel(nursingLevelDto);
        return ResponseResult.success();
    }

    @GetMapping("/{id}")
    @ApiOperation("根据ID查询")
    public ResponseResult<NursingLevelVo> getNursingLevelById(@PathVariable Long id) {
        NursingLevelVo nursingLevelVo = nursingLevelService.getNursingLevelById(id);
        return ResponseResult.success(nursingLevelVo);
    }

    @PutMapping("/update")
    @ApiOperation("修改")
    public ResponseResult updateNursingLevel(@RequestBody NursingLevelDto nursingLevelDto) {
        nursingLevelService.updateNursingLevel(nursingLevelDto);
        return ResponseResult.success();
    }
    @DeleteMapping("/delete/{id}")
    @ApiOperation("删除")
    public ResponseResult deleteNursingLevel(@PathVariable Long id){
        nursingLevelService.deleteNursingLevel(id);
        return ResponseResult.success();
    }

    @PutMapping("/{id}/status/{status}")
    @ApiOperation("禁用或开启状态")
    public ResponseResult<Void> updateStatus(@PathVariable Long id, @PathVariable Integer status) {
        nursingLevelService.updateStatus(id, status);
        return ResponseResult.success();
    }
}
