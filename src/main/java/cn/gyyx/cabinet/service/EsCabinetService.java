package cn.gyyx.cabinet.service;

import cn.gyyx.cabinet.domain.EsCabinet;

import java.util.List;

/**
 * @author feng
 * @date 2023/10/26 3:17 PM
 * @email:dujiafeng@gyyx.com
 * @description:
 */
public interface EsCabinetService {
    int importAll();


    void delete(Long id);


    EsCabinet create(Long id);


    void delete(List<Long> ids);
}
