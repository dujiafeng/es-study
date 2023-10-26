package cn.gyyx.cabinet.service;

import cn.gyyx.cabinet.domain.EsCabinet;
import cn.gyyx.cabinet.pojo.CabinetVo;
import org.springframework.data.domain.Page;

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

    Page<EsCabinet> search(String keyword, Integer pageNum, Integer pageSize);


    Page<CabinetVo> search(String lat, String lon, Integer distance, String disUnit, Integer pageNum, Integer pageSize);
}
