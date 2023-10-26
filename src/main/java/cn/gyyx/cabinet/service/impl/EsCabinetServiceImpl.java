package cn.gyyx.cabinet.service.impl;

import cn.gyyx.cabinet.domain.EsCabinet;
import cn.gyyx.cabinet.mapper.CabinetMapper;
import cn.gyyx.cabinet.pojo.Cabinet;
import cn.gyyx.cabinet.repository.EsCabinetRepository;
import cn.gyyx.cabinet.service.EsCabinetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author feng
 * @date 2023/10/26 3:19 PM
 * @email:dujiafeng@gyyx.com
 * @description:
 */
@Service
public class EsCabinetServiceImpl implements EsCabinetService {


    private final EsCabinetRepository cabinetRepository;

    private final CabinetMapper cabinetMapper;

    private final ElasticsearchRestTemplate elasticsearchRestTemplate;


    public EsCabinetServiceImpl(EsCabinetRepository cabinetRepository, CabinetMapper cabinetMapper, ElasticsearchRestTemplate elasticsearchRestTemplate) {
        this.cabinetRepository = cabinetRepository;
        this.cabinetMapper = cabinetMapper;
        this.elasticsearchRestTemplate = elasticsearchRestTemplate;
    }

    @Override
    public int importAll() {
        List<Cabinet> cabinetList = cabinetMapper.selectList(null);
        List<EsCabinet> esCabinetList = cabinetList.stream().map(Cabinet::toEsCabinet).collect(Collectors.toList());
        Iterable<EsCabinet> esProductIterable = cabinetRepository.saveAll(esCabinetList);
        Iterator<EsCabinet> iterator = esProductIterable.iterator();
        int result = 0;
        while (iterator.hasNext()) {
            result++;
            iterator.next();
        }
        return result;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public EsCabinet create(Long id) {
        return null;
    }

    @Override
    public void delete(List<Long> ids) {

    }
}
