package com.zzyl.controller.customer;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.zzyl.base.PageResponse;
import com.zzyl.base.ResponseResult;
import com.zzyl.dto.ReservationDto;
import com.zzyl.entity.Reservation;
import com.zzyl.entity.ReservationRecord;
import com.zzyl.service.ReservationService;
import com.zzyl.utils.UserThreadLocal;
import com.zzyl.vo.ReservationVo;
import com.zzyl.vo.TimeCountVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.zzyl.base.ResponseResult.success;

/**
 * @description:
 * @author: 李研
 * @date: 2024/10/25
 */
@RestController
@RequestMapping("/customer/reservation")
@Api(tags = "参观预约")
public class CustomerReservationConntroller {
    @Autowired
    private ReservationService reservationService;
    @GetMapping("/cancelled-count")
    @ApiOperation("获取已取消的次数")
    private ResponseResult<Integer> getCancelledCount(){
        //获取用户ID
        Long userId = UserThreadLocal.getUserId();
        //获取取消的次数
        int cancelledCount  = reservationService.countCancelledReservationsToday(userId);

        return success(cancelledCount);
    }
    @GetMapping("/countByTime")
    public ResponseResult<?> countByTime(Long time){
        List<TimeCountVo> list = reservationService.countReservationsForEachTimeWithinTimeRange(LocalDateTimeUtil.of(time));
        return success(list);
    }
    @PostMapping
    public ResponseResult createReservation(@RequestBody ReservationDto reservationDto) {
        // 调用服务层保存预约
        reservationService.saveReservation(reservationDto);
        return success();
    }
    @GetMapping("/page")
    @ApiOperation("分页查询预约")
    public ResponseResult<PageResponse> page(Integer pageNum, Integer pageSize, Integer status) {
        PageResponse<ReservationVo> pageResponse = reservationService.getReservationPage(pageNum,pageSize,status);
        return success(pageResponse);
    }

    @PutMapping("/{id}/cancel")
    @ApiOperation("取消预约")
    public ResponseResult<?> cancelReservation(@PathVariable("id") Long id) {
        boolean success = reservationService.cancelReservation(id);
        if (success){
            return success();

        }else {
            return ResponseResult.error();
        }
    }
}
