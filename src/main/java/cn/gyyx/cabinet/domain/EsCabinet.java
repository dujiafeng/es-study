package cn.gyyx.cabinet.domain;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;


import java.io.Serializable;
import java.time.LocalDateTime;


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
    private String cityCode;
    @Field(type = FieldType.Keyword)
    private String city;
    @Field(type = FieldType.Keyword)
    private String adCode;
    @Field(type = FieldType.Keyword)
    private String district;
    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String address;
    private Integer sts;

    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second)
    private LocalDateTime cTime;

    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second)
    private LocalDateTime uTime;

    private Integer isChanged;
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

    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second)
    private LocalDateTime  bTime;
}
