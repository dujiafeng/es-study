package cn.gyyx.cabinet.pojo;

import cn.gyyx.cabinet.domain.EsCabinet;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;


import java.util.Date;

/**
 * @author feng
 * @date 2023/10/24 4:39 PM
 * @email:dujiafeng@gyyx.com
 * @description:
 */
@Data
public class Cabinet {

    @TableId(type = IdType.INPUT)
    private Long id;
    private String cabid;
    private String plat;
    private String location;
    private String location_gcj02;
    private String province;
    private String citycode;
    private String city;
    private String adcode;
    private String district;
    private String address;
    private int sts;
    private Date ctime;
    private Date utime;
    private int ischanged;
    private String stationid;
    private int h3level;
    private int capacity;
    private int abnormal;
    private int valid48;
    private int charging48;
    private int valid60;
    private int charging60;
    private int invalid48;
    private int invalid60;
    private int valid72;
    private int charging72;
    private int invalid72;
    private Date btime;


    public EsCabinet toEsCabinet(){
        EsCabinet esCabinet = new EsCabinet();
        esCabinet.setId(id);
        esCabinet.setCabId(cabid);
        esCabinet.setPlat(plat);
        if (StringUtils.isNotBlank(location)){
            String[] split = location.split(",");
            GeoPoint geoPoint = new GeoPoint(Double.parseDouble(split[1]),Double.parseDouble(split[0]));
            esCabinet.setLocation(geoPoint);
        }
        if (StringUtils.isNotBlank(location_gcj02)){
            String[] split = location.split(",");
            GeoPoint geoPoint = new GeoPoint(Double.parseDouble(split[1]),Double.parseDouble(split[0]));
            esCabinet.setLocation_gcj02(geoPoint);
        }
        esCabinet.setProvince(province);
        esCabinet.setCitycode(citycode);
        esCabinet.setCity(city);
        esCabinet.setAdcode(adcode);
        esCabinet.setDistrict(district);
        esCabinet.setAddress(address);
        esCabinet.setSts(sts);
        esCabinet.setCTime(ctime);
        esCabinet.setUTime(utime);
        esCabinet.setIschanged(ischanged);
        esCabinet.setStationId(stationid);
        esCabinet.setH3level(h3level);
        esCabinet.setCapacity(capacity);
        esCabinet.setAbnormal(abnormal);
        esCabinet.setValid48(valid48);
        esCabinet.setCharging48(charging48);
        esCabinet.setValid60(valid60);
        esCabinet.setCharging60(charging60);
        esCabinet.setInvalid48(invalid48);
        esCabinet.setInvalid48(invalid60);
        esCabinet.setValid72(valid72);
        esCabinet.setCharging72(charging72);
        esCabinet.setInvalid72(invalid72);
        esCabinet.setBtime(btime);
        return esCabinet;

    }
}
