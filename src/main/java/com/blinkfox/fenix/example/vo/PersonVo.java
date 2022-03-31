package com.blinkfox.fenix.example.vo;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 查询后的 Person 部分结果信息.
 *
 * @author blinkfox on 2022-03-31.
 * @since 2.7.0
 */
@Getter
@Setter
@Accessors(chain = true)
public class PersonVo {

    private String id;

    private String account;

    private String nickName;

    private Integer status;

    private LocalDateTime lastUpdateTime;

}
