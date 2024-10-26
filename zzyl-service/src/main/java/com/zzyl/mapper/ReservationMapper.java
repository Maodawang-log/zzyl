package com.zzyl.mapper;

import com.github.pagehelper.Page;
import com.zzyl.entity.Reservation;
import com.zzyl.entity.ReservationRecord;
import com.zzyl.vo.TimeCountVo;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface ReservationMapper {
    @Select("SELECT COUNT(*) FROM reservation WHERE create_by = #{userId} AND status = 2 AND DATE(create_time) = CURRENT_DATE()")
    int countCancelledReservationsToday(@Param("userId") Long userId);

    @Select("select time ,COUNT(*) count from reservation where status = 0 group by time")
    List<TimeCountVo> findReservationsByDate(LocalDate time);


    @Insert("INSERT INTO reservation (name, mobile, time, visitor, type, status, create_time, update_time, create_by, update_by, remark) " +
            "VALUES (#{name}, #{mobile}, #{time}, #{visitor}, #{type}, #{status}, #{createTime}, #{updateTime}, #{createBy}, #{updateBy}, #{remark})")
    void insertReservation(Reservation reservation);

    @Select("SELECT COUNT(*) FROM reservation WHERE mobile = #{mobile} AND time = #{time}")
    int countReservationByMobileAndTime(@Param("mobile") String mobile, @Param("time") LocalDateTime time);


    Page<ReservationRecord> selectByPage(Integer status,long id);

    @Select("SELECT * FROM reservation WHERE id = #{id}")
    Reservation findById(@Param("id") Long id);

    @Update("UPDATE reservation SET status = #{status} WHERE id = #{id}")
    void updateReservationStatus(Reservation reservation);

    @Select("SELECT * FROM reservation WHERE status = 0 AND time < NOW()")
    List<Reservation> findUnconfirmedReservations();
}
