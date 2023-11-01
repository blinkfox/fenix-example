package com.blinkfox.fenix.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 图书实体类.
 *
 * @author blinkfox on 2020-12-07.
 * @since v2.4.0
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity
@Table(name = "t_book", schema = "test")
@NoArgsConstructor
public class Book {

    /**
     * ID.
     */
    @Id
    @Column(name = "c_id")
    private String id;

    /**
     * 图书的 ISBN 编号.
     */
    @Column(name = "c_isbn")
    private String isbn;

    /**
     * 图书标题.
     */
    @Column(name = "c_name")
    private String name;

    /**
     * 图书作者.
     */
    @Column(name = "c_author")
    private String author;

    /**
     * 图书内容简介.
     */
    @Column(name = "c_summary")
    private String summary;

    /**
     * 总页数.
     */
    @Column(name = "n_total_page")
    private int totalPage;

    /**
     * 出版社.
     */
    @Column(name = "c_publishing_house")
    private String publishingHouse;

    /**
     * 出版日期.
     */
    @Column(name = "c_publish_at")
    private String publishAt;

    /**
     * 创建时间.
     */
    @Column(name = "dt_create_time")
    private Date createTime;

    /**
     * 最后更新时间.
     */
    @Column(name = "dt_update_time")
    private Date updateTime;

}
