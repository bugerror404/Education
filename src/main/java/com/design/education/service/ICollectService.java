package com.design.education.service;

import com.design.education.entity.Collect;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

@Service
public interface ICollectService {
    void insertCollect(Integer id,Collect collect);

    Collect selectCollectById(Integer id);
//根据用户id查询课程
    Collect selectCollectByuId(Integer id);

    void deleteClassByaId(Integer id);



    //PageInfo<Collect> selectCollectsByuId(int page, int count, String id);

}
