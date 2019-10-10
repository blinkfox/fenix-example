package com.blinkfox.fenix.example.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 自定义的博客部分信息的实体类.
 *
 * @author blinkfox on 2019-10-10.
 * @since v1.1.0
 */
@Getter
@Setter
public class BlogDto {

    /**
     * 博客 ID.
     */
    private String blogId;

    /**
     * 用户 ID.
     */
    private String userId;

    /**
     * 博客标题.
     */
    private String title;

    /**
     * 博客作者.
     */
    private String author;

}
