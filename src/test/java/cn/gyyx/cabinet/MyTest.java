package cn.gyyx.cabinet;

import cn.gyyx.cabinet.domain.EsCabinet;
import cn.gyyx.cabinet.pojo.CabinetVo;
import cn.gyyx.cabinet.service.EsCabinetService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author feng
 * @date 2023/10/25 6:12 PM
 * @email:dujiafeng@gyyx.com
 * @description:
 */
@SpringBootTest
@Slf4j
public class MyTest {


    @Resource
    EsCabinetService esCabinetService;

    @Test
    public void all(){
        int count = esCabinetService.importAll();
        log.info("count:{}",count);
    }

    @Test
    public void search(){
        Page<CabinetVo> cabinetPage = esCabinetService.search("40.159866", "116.297043", 5, "km", 0, 100);
        log.info("success...");
        System.out.println(cabinetPage);
    }




}
