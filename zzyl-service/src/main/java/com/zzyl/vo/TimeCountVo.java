package com.zzyl.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @description:
 * @author: 李研
 * @date: 2024/10/25
 */
@Data
public class TimeCountVo {
    private LocalDateTime time;
    private Integer count;
}
