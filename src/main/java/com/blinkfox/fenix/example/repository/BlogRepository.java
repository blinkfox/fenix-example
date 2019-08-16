package com.blinkfox.fenix.example.repository;

import com.blinkfox.fenix.example.entity.Blog;
import com.blinkfox.fenix.jpa.QueryFenix;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

/**
 * BlogRepository.
 *
 * @author blinkfox on 2019-08-16.
 */
public interface BlogRepository extends JpaRepository<Blog, String> {

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

}
