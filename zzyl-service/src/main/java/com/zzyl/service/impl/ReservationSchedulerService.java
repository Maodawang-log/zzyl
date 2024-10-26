package com.zzyl.service.impl;

import com.zzyl.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ReservationSchedulerService {

    @Autowired
    private ReservationService reservationService;

    // 每小时的第 1 分钟和第 31 分钟执行
    @Scheduled(cron = "0 1,31 * * * ?")
    public void checkAndCancelUnconfirmedReservations() {
        System.out.println("开始检查未确认的预约...");
        reservationService.cancelUnconfirmedReservations();
        System.out.println("检查完成.");
    }
}
