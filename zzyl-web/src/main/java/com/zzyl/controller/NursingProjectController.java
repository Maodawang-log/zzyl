package com.zzyl.controller;

import com.zzyl.base.PageResponse;
import com.zzyl.base.ResponseResult;
import com.zzyl.dto.NursingProjectDto;
import com.zzyl.entity.NursingProject;
import com.zzyl.service.NursingProjectService;
import com.zzyl.vo.NursingProjectVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 护理项目Controller类
 */
@RestController
@RequestMapping("/nursing_project")
@Api(tags = "护理项目管理")
public class NursingProjectController {

    @Autowired
    private NursingProjectService nursingProjectService;

    @GetMapping
    @ApiOperation("分页查询护理项目列表")
    public ResponseResult<PageResponse<NursingProjectVo>> getByPage(
            @ApiParam(value = "护理项目名称")
            @RequestParam(value = "name", required = false) String name,
            @ApiParam(value = "状态：0-禁用，1-启用")
            @RequestParam(value = "status", required = false) Integer status,
            @ApiParam(value = "当前页码")
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @ApiParam(value = "每页显示数量")
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        PageResponse<NursingProjectVo> nursingProjectPageInfo = nursingProjectService.getByPage(name, status, pageNum, pageSize);
        return ResponseResult.success(nursingProjectPageInfo);
    }

    @PostMapping
    @ApiOperation("新增护理项目")
    public ResponseResult add(
            @ApiParam(value = "护理项目数据传输对象", required = true)
            @RequestBody NursingProjectDto nursingProjectDTO) {
        nursingProjectService.add(nursingProjectDTO);
        return ResponseResult.success();
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id查询护理项目")
    public ResponseResult<NursingProjectVo> getById(@PathVariable("id") Long id) {
        NursingProjectVo nursingProjectVO = nursingProjectService.getById(id);
        return ResponseResult.success(nursingProjectVO);
    }

    @PutMapping
    @ApiOperation("修改护理项目")
    public ResponseResult update(@RequestBody NursingProjectDto nursingProjectDTO) {
        nursingProjectService.update(nursingProjectDTO);
        return ResponseResult.success();
    }
    @PutMapping("/{id}/status/{status}")
    @ApiOperation("修改禁用状态")
    public ResponseResult disableNursingProject(@PathVariable Long id, @PathVariable int status){
        nursingProjectService.of(id,status);
        return ResponseResult.success();
    }
    @DeleteMapping("/{id}")
    @ApiOperation("删除护理项目")
    public ResponseResult delNursingProject(@PathVariable Long id){
        nursingProjectService.del(id);
        return ResponseResult.success();
    }
    @GetMapping("/all")
    @ApiOperation("查询所有护理项目")
    public ResponseResult getALL(){
        List<NursingProjectVo> all = nursingProjectService.getAllProjects();
        return ResponseResult.success(all);
    }
}