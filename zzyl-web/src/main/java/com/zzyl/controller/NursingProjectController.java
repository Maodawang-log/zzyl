package com.zzyl.controller;

import com.zzyl.base.PageResponse;
import com.zzyl.base.ResponseResult;
import com.zzyl.service.NursingProjectService;
import com.zzyl.vo.NursingProjectVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

}