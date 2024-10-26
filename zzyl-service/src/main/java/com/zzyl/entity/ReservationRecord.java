package com.zzyl.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationRecord {
    private Long id;                   // 主键ID
    private LocalDateTime createTime;  // 创建时间
    private Long createBy;             // 创建人ID
    private String creator;            // 创建人姓名
    private String name;               // 预约人姓名
    private String mobile;             // 预约人手机号
    private LocalDateTime time;        // 预约时间
    private String visitor;            // 探访人
    private int type;                  // 预约类型
    private int status;                // 预约状态
}
