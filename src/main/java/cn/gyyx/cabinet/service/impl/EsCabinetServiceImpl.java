package cn.gyyx.cabinet.service.impl;

import cn.gyyx.cabinet.domain.EsCabinet;
import cn.gyyx.cabinet.mapper.CabinetMapper;
import cn.gyyx.cabinet.pojo.Cabinet;
import cn.gyyx.cabinet.pojo.CabinetVo;
import cn.gyyx.cabinet.repository.EsCabinetRepository;
import cn.gyyx.cabinet.service.EsCabinetService;
import com.fasterxml.jackson.databind.util.BeanUtil;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.GeoDistanceQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
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
        cabinetRepository.deleteById(id);
    }

    @Override
    public EsCabinet create(Long id) {
        EsCabinet esCabinet = null;
        Cabinet cabinet = cabinetMapper.selectById(id);
        if (cabinet!=null){
            esCabinet = cabinet.toEsCabinet();
            cabinetRepository.save(esCabinet);
        }
        return esCabinet;
    }

    @Override
    public void delete(List<Long> ids) {
        if (!CollectionUtils.isEmpty(ids)) {
            List<EsCabinet> esProductList = new ArrayList<>();
            for (Long id : ids) {
                EsCabinet esCabinet = new EsCabinet();
                esCabinet.setId(id);
                esProductList.add(esCabinet);
            }
            cabinetRepository.deleteAll(esProductList);
        }
    }

    @Override
    public Page<EsCabinet> search(String keyword, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return cabinetRepository.findByAddress(keyword, keyword, keyword, pageable);
    }

    @Override
    public Page<CabinetVo> search(String lat, String lon, Integer distance, String disUnit, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        //分页
        nativeSearchQueryBuilder.withPageable(pageable);
        if (lat!=null&&lon!=null){
            GeoPoint geoPoint = new GeoPoint(lat + "," + lon);
            GeoDistanceQueryBuilder distanceQueryBuilder = QueryBuilders.geoDistanceQuery("location");
            distanceQueryBuilder.point(geoPoint);
            if (distance==null){
                distanceQueryBuilder.distance(1, DistanceUnit.KILOMETERS);
            }else{
                distanceQueryBuilder.distance(distance+disUnit);
            }
            nativeSearchQueryBuilder.withFilter(distanceQueryBuilder);
            nativeSearchQueryBuilder.withSort(SortBuilders.geoDistanceSort("location",geoPoint).unit(DistanceUnit.KILOMETERS).order(SortOrder.ASC));
        }

        NativeSearchQuery searchQuery = nativeSearchQueryBuilder.build();

        SearchHits<EsCabinet> searchHits = elasticsearchRestTemplate.search(searchQuery, EsCabinet.class);
        if (searchHits.getTotalHits()<=0){
            return new PageImpl<>(Collections.emptyList(),pageable,0);
        }

        List<CabinetVo> cabinetVoList = new ArrayList<>();
        searchHits.getSearchHits().forEach(item->{
            EsCabinet content = item.getContent();
            CabinetVo cabinetVo = new CabinetVo();
            BeanUtils.copyProperties(content,cabinetVo);
            List<Object> sortValues = item.getSortValues();
            BigDecimal pointDistance = new BigDecimal(sortValues.get(0).toString()).setScale(2, RoundingMode.HALF_UP);
            cabinetVo.setDistance(pointDistance);
            cabinetVoList.add(cabinetVo);
        });


        //List<EsCabinet> searchCabinet = searchHits.stream().map(SearchHit::getContent).collect(Collectors.toList());
        return new PageImpl<>(cabinetVoList,pageable,searchHits.getTotalHits());
    }

}
