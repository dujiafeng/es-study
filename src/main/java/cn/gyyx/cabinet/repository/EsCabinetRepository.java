package cn.gyyx.cabinet.repository;

import cn.gyyx.cabinet.domain.EsCabinet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author feng
 * @date 2023/10/26 3:14 PM
 * @email:dujiafeng@gyyx.com
 * @description:
 */
public interface EsCabinetRepository extends ElasticsearchRepository<EsCabinet, Long> {

    Page<EsCabinet> findByAddress(String keyword, String keyword1, String keyword2, Pageable pageable);

}
