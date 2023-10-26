package cn.gyyx.cabinet.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import java.io.Serializable;
import java.util.Date;

/**
 * @author feng
 * @date 2023/10/26 2:28 PM
 * @email:dujiafeng@gyyx.com
 * @description:
 */
@Document(indexName = "cabinet")
@Data
public class EsCabinet implements Serializable {
    private static final long serialVersionUID = -1L;

    @Id
    private Long id;
    @Field(type = FieldType.Keyword)
    private String cabId;
    @Field(type = FieldType.Keyword)
    private String plat;
    @GeoPointField
    private GeoPoint location;
    @GeoPointField
    private GeoPoint location_gcj02;
    @Field(type = FieldType.Keyword)
    private String province;
    @Field(type = FieldType.Keyword)
    private String citycode;
    @Field(type = FieldType.Keyword)
    private String city;
    @Field(type = FieldType.Keyword)
    private String adcode;
    @Field(type = FieldType.Keyword)
    private String district;
    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String address;
    private Integer sts;
    @Field(type = FieldType.Date,format=DateFormat.custom,pattern="yyyy-MM-dd HH:mm:ss")
    private Date cTime;
    @Field(type = FieldType.Date,format=DateFormat.custom,pattern="yyyy-MM-dd HH:mm:ss")
    private Date uTime;
    private Integer ischanged;
    private String stationId;
    private Integer h3level;
    private Integer capacity;
    private Integer abnormal;
    private Integer valid48;
    private Integer charging48;
    private Integer valid60;
    private Integer charging60;
    private Integer invalid48;
    private Integer invalid60;
    private Integer valid72;
    private Integer charging72;
    private Integer invalid72;
    @Field(type = FieldType.Date,format=DateFormat.custom,pattern="yyyy-MM-dd HH:mm:ss")
    private Date btime;
}
