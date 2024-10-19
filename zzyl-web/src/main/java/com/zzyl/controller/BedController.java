package com.zzyl.controller;

import com.zzyl.base.ResponseResult;
import com.zzyl.dto.BedDto;
import com.zzyl.service.BedService;
import com.zzyl.vo.BedVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bed")
@Api(tags = "床位管理相关接口")
public class BedController extends BaseController {

    @Autowired
    private BedService bedService;

    @GetMapping("/read/room/{roomId}")
    @ApiOperation(value = "根据房间id查询床位", notes = "传入房间id")
    public ResponseResult<List<BedVo>> readBedByRoomId(
            @ApiParam(value = "房间ID", required = true) @PathVariable("roomId") Long roomId) {
        List<BedVo> beds = bedService.getBedsByRoomId(roomId);
        return success(beds);
    }

    @PostMapping("/create")
    public ResponseResult createBed(@RequestBody BedDto bedDto) {
        bedService.addBed(bedDto);
        return success();
    }

    @GetMapping("/read/{id}")
    public ResponseResult readBed(@PathVariable long id){
        BedVo bed = bedService.getBedById(id);
        return success(bed);
    }

    @PutMapping("/update")
    public ResponseResult updateBed(@RequestBody BedDto bed) {
        bedService.updateBed(bed);
        return success();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseResult deleteBed(@PathVariable("id") Long id) {
        bedService.deleteBedById(id);
        return success();
    }
}
