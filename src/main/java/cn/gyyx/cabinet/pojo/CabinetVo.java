package cn.gyyx.cabinet.pojo;


import cn.gyyx.cabinet.domain.EsCabinet;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
public class CabinetVo extends EsCabinet {
    private BigDecimal distance;
}
