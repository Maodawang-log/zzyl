package com.zzyl.service;

import com.zzyl.base.PageResponse;
import com.zzyl.dto.ReservationDto;
import com.zzyl.vo.ReservationVo;
import com.zzyl.vo.TimeCountVo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface ReservationService {

    public int countCancelledReservationsToday(Long userId);

    List<TimeCountVo> countReservationsForEachTimeWithinTimeRange(LocalDateTime time);

    void saveReservation(ReservationDto reservationDto);

    PageResponse<ReservationVo> getReservationPage(Integer pageNum, Integer pageSize, Integer status);

    boolean cancelReservation(Long id);

    void cancelUnconfirmedReservations();
}
