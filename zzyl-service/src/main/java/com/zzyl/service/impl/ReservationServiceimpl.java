package com.zzyl.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zzyl.base.PageResponse;
import com.zzyl.dto.ReservationDto;
import com.zzyl.entity.Reservation;
import com.zzyl.entity.ReservationRecord;
import com.zzyl.mapper.ReservationMapper;
import com.zzyl.service.ReservationService;
import com.zzyl.utils.UserThreadLocal;
import com.zzyl.vo.ReservationVo;
import com.zzyl.vo.TimeCountVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @description:
 * @author: 李研
 * @date: 2024/10/25
 */
@Service
public class ReservationServiceimpl implements ReservationService {
    @Autowired
    private ReservationMapper reservationMapper;
    @Override
    public int countCancelledReservationsToday(Long userId) {
        return reservationMapper.countCancelledReservationsToday(userId);
    }

    @Override
    public List<TimeCountVo> countReservationsForEachTimeWithinTimeRange(LocalDateTime time) {
        //返回已预约了多少次
        List<TimeCountVo> reservationsByDate = reservationMapper.findReservationsByDate(LocalDate.from(time));
        //每个时间段只能预约6次，计算还剩多少次预约
        reservationsByDate.forEach(a -> a.setCount(6 - a.getCount()));
        return  reservationsByDate;
    }

    @Override
    public void saveReservation(ReservationDto reservationDto) {
        // 设置状态默认值
        reservationDto.setStatus(0);

        // 检查是否在相同时间段内使用相同手机号预约
        int existingReservations = reservationMapper.countReservationByMobileAndTime(reservationDto.getMobile(), reservationDto.getTime());
        if (existingReservations > 0) {
            throw new RuntimeException("相同时间段内已使用该手机号预约，无法再次预约");
        }

        // 如果没有冲突，继续保存预约
        Reservation reservation = BeanUtil.toBean(reservationDto, Reservation.class);
        reservationMapper.insertReservation(reservation);
    }
    @Override
    public PageResponse<ReservationVo> getReservationPage(Integer pageNum, Integer pageSize, Integer status) {
        PageHelper.startPage(pageNum,pageSize);
        Page<ReservationRecord> page = reservationMapper.selectByPage(status,UserThreadLocal.getUserId());
        return PageResponse.of(page, ReservationVo.class);
    }
    //取消预约
    @Override
    public boolean cancelReservation(Long id) {
        Reservation reservation = reservationMapper.findById(id);
        if (reservation != null && reservation.getStatus() != 2) {
            reservation.setStatus(2);
            reservationMapper.updateReservationStatus(reservation);
            return true;
        }
        return false;
    }

    public void cancelUnconfirmedReservations() {
        // 查询所有未确认的预约
        List<Reservation> unconfirmedReservations = reservationMapper.findUnconfirmedReservations();
        for (Reservation reservation : unconfirmedReservations) {
            // 将状态更新为过期
            reservation.setStatus(3); // 状态 2 表示已过期
            reservationMapper.updateReservationStatus(reservation);
        }
    }
}
