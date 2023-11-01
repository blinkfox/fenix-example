package com.blinkfox.fenix.example.handler;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 为了测试自定义标签的用户实体类.
 *
 * @author blinkfox on 2019-08-17.
 */
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "t_user", schema = "test")
class User {

    /**
     * ID.
     */
    @Id
    @Column(name = "c_id")
    private String id;

    /**
     * 名称.
     */
    @Column(name = "c_name")
    private String name;

    /**
     * 所在地区，6位数的地区编码，前两位是省份份，前四位是地市.
     */
    @Column(name = "c_region")
    private String region;

    /**
     * 用户地区级别，0表示中央级，1表示省级，2表示地市级，3表示区县级.
     */
    @Column(name = "n_level")
    private int level;

}
