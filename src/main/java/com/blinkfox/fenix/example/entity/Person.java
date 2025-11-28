package com.blinkfox.fenix.example.entity;

import com.blinkfox.fenix.ar.repo.JpaModel;
import com.blinkfox.fenix.ar.spec.FenixSpecModel;
import com.blinkfox.fenix.example.repository.PersonRepository;
import com.blinkfox.fenix.id.Snowflake36RadixId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 用户信息实体类.
 *
 * @author blinkfox on 2022-03-31.
 * @since 2.7.0
 */
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "t_person", schema = "test")
public class Person implements JpaModel<Person, String, PersonRepository>, FenixSpecModel<Person, PersonRepository> {

    /**
     * 使用 36 进制的雪花算法 ID 生成策略.
     */
    @Id
    @Column(name = "c_id")
    @Snowflake36RadixId
    private String id;

    /**
     * 用户账户.
     */
    @Column(name = "c_account")
    private String account;

    /**
     * 用户昵称.
     */
    @Column(name = "c_nick_name")
    private String nickName;

    /**
     * 用户年龄.
     */
    @Column(name = "n_age")
    private Integer age;

    /**
     * 用户出生日期.
     */
    @Column(name = "d_birthday")
    private LocalDate birthday;

    /**
     * 用户状态.
     */
    @Column(name = "n_status")
    private Integer status;

    /**
     * 创建时间.
     */
    @Column(name = "dt_create_time")
    private Date createTime;

    /**
     * 最后更新时间.
     */
    @Column(name = "dt_last_update_time")
    private LocalDateTime lastUpdateTime;

}
