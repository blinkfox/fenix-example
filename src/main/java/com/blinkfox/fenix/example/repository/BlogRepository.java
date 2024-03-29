package com.blinkfox.fenix.example.repository;

import com.blinkfox.fenix.example.entity.Blog;
import com.blinkfox.fenix.example.provider.BlogSqlProvider;
import com.blinkfox.fenix.example.vo.BlogVo;
import com.blinkfox.fenix.jpa.QueryFenix;
import com.blinkfox.fenix.specification.FenixJpaSpecificationExecutor;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

/**
 * 博客信息查询仓库.
 *
 * @author blinkfox on 2019-08-16.
 */
public interface BlogRepository extends JpaRepository<Blog, String>, FenixJpaSpecificationExecutor<Blog> {

    /**
     * 使用 {@link QueryFenix} 注解来演示根据博客信息Bean(可以是其它Bean 或者 Map)来多条件模糊分页查询博客信息.
     *
     * @param ids 博客信息 ID 集合
     * @param blog 博客信息实体类，可以是其它 Bean 或者 Map.
     * @param pageable JPA 分页排序参数
     * @return 博客分页信息
     */
    @QueryFenix(countQuery = "queryMyBlogsCount")
    Page<Blog> queryMyBlogs(@Param("ids") List<String> ids, @Param("blog") Blog blog, Pageable pageable);

    /**
     * 使用 {@link QueryFenix} 注解来演示根据任意参数来多条件模糊查询博客信息.
     *
     * @param ids 博客信息 ID 集合
     * @param blog 博客信息实体类，可以是其它 Bean 或者 Map.
     * @return 博客信息
     */
    @QueryFenix
    List<Blog> queryBlogsByTemplate(@Param("ids") List<String> ids, @Param("blog") Blog blog);

    /**
     * 使用 {@link QueryFenix} 注解和 Java API 来拼接 SQL 的方式来查询博客信息.
     *
     * @param blog 博客信息实体
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param blogIds 博客 ID 集合
     * @return 用户信息集合
     */
    @QueryFenix(provider = BlogSqlProvider.class)
    List<Blog> queryBlogsWithJava(@Param("blog") Blog blog, @Param("startTime") Date startTime,
            @Param("endTime") Date endTime, @Param("blogIds") String[] blogIds);

    /**
     * 使用 {@link QueryFenix} 注解来演示根据任意参数来多条件模糊查询博客信息，并返回一个自定义的实体对象.
     *
     * @param ids 博客信息 ID 集合
     * @param title 博客标题
     * @param pageable JPA 分页排序参数
     * @return 自定义的博客部分信息实体类
     * @since v1.1.0
     */
    @QueryFenix
    Page<BlogVo> queryBlogVos(@Param("ids") List<String> ids, @Param("title") String title, Pageable pageable);

    /**
     * 2.7.0 及之后的版本的 resultType 新写法，建议将 resultType 写到 {@link QueryFenix#resultType()} 的注解属性中，
     * 能更好的利用上 Java 静态编译检查功能，利于代码重构和 IDE 的跳转查看等.
     *
     * @param ids 博客信息 ID 集合
     * @param title 博客标题
     * @param pageable JPA 分页排序参数
     * @return 自定义的博客部分信息实体类
     * @since v1.1.0
     */
    @QueryFenix(resultType = BlogVo.class)
    Page<BlogVo> queryNewBlogVos(@Param("ids") List<String> ids, @Param("title") String title, Pageable pageable);

}
