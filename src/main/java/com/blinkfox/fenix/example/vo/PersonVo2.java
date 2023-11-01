package com.blinkfox.fenix.example.vo;

import jakarta.persistence.Column;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 查询后的 Person 部分结果信息，本 VO 类带有 {@link Column} 注解，用于转换结果.
 *
 * @author blinkfox on 2022-03-31.
 * @since 2.7.0
 */
@Getter
@Setter
@Accessors(chain = true)
public class PersonVo2 {

    @Column(name = "c_id")
    private String id;

    @Column(name = "c_nick_name")
    private String nickName;

    @Column(name = "n_status")
    private Integer status;

    @Column(name = "dt_last_update_time")
    private LocalDateTime lastUpdateTime;

}
