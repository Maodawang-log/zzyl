package com.zzyl.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zzyl.base.PageResponse;
import com.zzyl.dto.NursingLevelDto;
import com.zzyl.entity.NursingLevel;
import com.zzyl.mapper.NursingLevelMapper;
import com.zzyl.service.NursingLevelService;
import com.zzyl.vo.NursingLevelVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NursingLevelServiceImpl implements NursingLevelService {

    @Autowired
    private NursingLevelMapper nursingLevelMapper;

    @Override
    public List<NursingLevelVo> getAllLevels() {
        List<NursingLevel> nursingLevels = nursingLevelMapper.listAll();
        return nursingLevels.stream().map(this::convertToVo).collect(Collectors.toList());
    }
    @Override
    public PageResponse<NursingLevelVo> getLevelsByPage(int pageNum, int pageSize, String name, Integer status) {
        PageHelper.startPage(pageNum, pageSize);
        Page<NursingLevel> nursingLevels = nursingLevelMapper.listByCondition(name, status);
        PageResponse<NursingLevelVo> LevelVoPageResponse = PageResponse.of(nursingLevels, NursingLevelVo.class);
        return LevelVoPageResponse;
    }
    @Override
    @Transactional
    public void insertNursingLevel(NursingLevelDto nursingLevelDto) {
        NursingLevel nursingLevel = convertToEntity(nursingLevelDto);
        nursingLevelMapper.insert(nursingLevel);
    }
    @Override
    public void updateNursingLevel(NursingLevelDto nursingLevelDto) {
        NursingLevel nursingLevel = convertToEntity(nursingLevelDto);
        nursingLevelMapper.updateNursingLevel(nursingLevel);
    }
    @Override
    public NursingLevelVo getNursingLevelById(Long id) {
        return nursingLevelMapper.getNursingLevelById(id);
    }

    @Override
    public void deleteNursingLevel(Long id) {
        nursingLevelMapper.deleteNursingLevel(id);
    }

    @Override
    public void updateStatus(Long id, Integer status) {
        NursingLevel nursingLevel = new NursingLevel();
        nursingLevel.setId(id);
        nursingLevel.setStatus(status);
        nursingLevelMapper.updateNursingLevel(nursingLevel);
    }

    private NursingLevel convertToEntity(NursingLevelDto dto) {
        NursingLevel entity = new NursingLevel();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }

    private NursingLevelVo convertToVo(NursingLevel nursingLevel) {
        NursingLevelVo vo = new NursingLevelVo();
        BeanUtils.copyProperties(nursingLevel, vo);
        return vo;
    }

}
