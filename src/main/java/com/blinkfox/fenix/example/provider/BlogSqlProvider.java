package com.blinkfox.fenix.example.provider;

import com.blinkfox.fenix.bean.SqlInfo;
import com.blinkfox.fenix.core.Fenix;
import com.blinkfox.fenix.example.entity.Blog;
import com.blinkfox.fenix.helper.CollectionHelper;
import com.blinkfox.fenix.helper.StringHelper;
import java.util.Date;
import org.springframework.data.repository.query.Param;

/**
 * 博客 {@link SqlInfo} 信息的提供类.
 *
 * @author blinkfox on 2019-08-17.
 */
public class BlogSqlProvider {

    /**
     * 通过 Java API 来拼接得到 {@link SqlInfo} 的方式来查询博客信息.
     *
     * @param blogIds 博客 ID 集合
     * @param blog 博客信息实体
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return {@link SqlInfo} 示例
     */
    public SqlInfo queryBlogsWithJava(@Param("blogIds") String[] blogIds, @Param("blog") Blog blog,
            @Param("startTime") Date startTime, @Param("endTime") Date endTime) {
        return Fenix.start()
                .select("b")
                .from("Blog").as("b")
                .where()
                .in("b.id", blogIds, CollectionHelper.isNotEmpty(blogIds))
                .andLike("b.title", blog.getTitle(), StringHelper.isNotBlank(blog.getTitle()))
                .andLike("b.author", blog.getAuthor(), StringHelper.isNotBlank(blog.getAuthor()))
                .andBetween("b.createTime", startTime, endTime, startTime != null || endTime != null)
                .end();
    }

}
