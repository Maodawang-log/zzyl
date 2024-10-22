package com.zzyl.service;

import com.github.pagehelper.Page;
import com.zzyl.base.PageResponse;
import com.zzyl.dto.NursingLevelDto;
import com.zzyl.vo.NursingLevelVo;

import java.util.List;

/**
 * @description:
 * @author: 李研
 * @date: 2024/10/22
 */

public interface NursingLevelService {
    List<NursingLevelVo> getAllLevels();

    PageResponse<NursingLevelVo> getLevelsByPage(int pageNum, int pageSize, String name, Integer status);

    public void insertNursingLevel(NursingLevelDto nursingLevelDto);

    void updateNursingLevel(NursingLevelDto nursingLevelDto);

    NursingLevelVo getNursingLevelById(Long id);

    void deleteNursingLevel(Long id);

    void updateStatus(Long id, Integer status);
}
