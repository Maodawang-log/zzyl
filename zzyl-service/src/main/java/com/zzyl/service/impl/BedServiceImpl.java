package com.zzyl.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.zzyl.dto.BedDto;
import com.zzyl.entity.Bed;
import com.zzyl.mapper.BedMapper;
import com.zzyl.service.BedService;
import com.zzyl.vo.BedVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BedServiceImpl implements BedService {

    @Autowired
    private BedMapper bedMapper;

    @Override
    public List<BedVo> getBedsByRoomId(Long roomId) {
        return bedMapper.getBedsByRoomId(roomId);
    }

    @Override
    public void addBed(BedDto bedDto) {
        Bed bed = BeanUtil.toBean(bedDto, Bed.class);
        bed.setCreateTime(LocalDateTime.now());
        bed.setCreateBy(1L);
        bed.setBedStatus(0);
        bedMapper.addBed(bed);
    }

    @Override
    public BedVo getBedById(long id) {
        return BeanUtil.toBean(bedMapper.getBedById(id), BedVo.class);
    }

    @Override
    public void updateBed(BedDto bedDto) {
        BedVo bedVo = getBedById(bedDto.getId());
        bedVo.setSort(bedDto.getSort());
        bedVo.setBedNumber(bedDto.getBedNumber());
        if (ObjectUtil.isNotEmpty(bedDto.getBedStatus())) {
            bedVo.setBedStatus(bedDto.getBedStatus());
        }
        Bed bed = BeanUtil.toBean(bedVo, Bed.class);
        bed.setCreateTime(LocalDateTime.now());
        bed.setCreateBy(1L);
        bedMapper.updateBed(bed);
    }

    @Override
    public void deleteBedById(Long id) {
        bedMapper.deleteBedById(id);
    }
}

