package cn.gyyx.cabinet.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author feng
 * @date 2023/10/26 2:44 PM
 * @email:dujiafeng@gyyx.com
 * @description:
 */

@Document(indexName = "hotel")
@Data
public class EsHotel implements Serializable {
    private static final long serialVersionUID = -1L;

    @Id
    private Long id;
    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String name;
    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String address;
    private BigDecimal price;
    private Integer score;
    private String brand;
    private String city;
    private String starName;
    private String business;
    @GeoPointField
    private String location;
    @Field(type = FieldType.Keyword)
    private String pic;
}
