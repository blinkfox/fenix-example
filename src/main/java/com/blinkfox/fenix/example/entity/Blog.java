package com.blinkfox.fenix.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 博客信息实体类.
 *
 * @author blinkfox on 2019-08-13.
 */
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "t_blog", schema = "test")
public class Blog {

    /**
     * ID.
     */
    @Id
    @Column(name = "c_id")
    private String id;

    /**
     * 发表博客的用户 ID.
     */
    @Column(name = "c_user_id")
    private String userId;

    /**
     * 博客标题.
     */
    @Column(name = "c_title")
    private String title;

    /**
     * 博客作者.
     */
    @Column(name = "c_author")
    private String author;

    /**
     * 博客内容.
     */
    @Column(name = "c_content")
    private String content;

    /**
     * 创建时间.
     */
    @Column(name = "dt_create_time")
    private Date createTime;

    /**
     * 更新时间.
     */
    @Column(name = "dt_update_time")
    private Date updateTime;

}
