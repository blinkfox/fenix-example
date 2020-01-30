package com.blinkfox.fenix.example.vo;

import com.blinkfox.fenix.specification.annotation.Between;
import com.blinkfox.fenix.specification.annotation.In;
import com.blinkfox.fenix.specification.annotation.Like;
import com.blinkfox.fenix.specification.handler.bean.BetweenValue;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 用于测试 {@code FenixSpecification} 注解动态查询的博客 VO 类.
 *
 * @author blinkfox on 2020-01-28.
 */
@Getter
@Setter
@Accessors(chain = true)
public class BlogParam {

    /**
     * 用于 IN 范围查询的 ID 集合，{@link In} 注解的属性值可以是数组，也可以是 {@link java.util.Collection} 集合数据.
     */
    @In("id")
    private List<String> ids;

    /**
     * 模糊查询博客信息的作者名称关键字内容的字符串.
     */
    @Like
    private String author;

    /**
     * 用于根据博客创建时间 {@link Between} 区间查询博客信息的开始值和介绍值，
     * 区间查询的值类型建议是 {@link BetweenValue} 类型的.
     * 当然值类型也可以是二元数组，也可以是 {@link List} 集合，如果是这两种类型的值，元素的顺序必须是开始值和结束值才行.
     */
    @Between("createTime")
    private BetweenValue<Date> createTime;

}
