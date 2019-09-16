package com.blinkfox.fenix.example.repository;

import com.blinkfox.fenix.example.entity.Blog;

import com.blinkfox.fenix.jpa.QueryFenix;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * BlogRepository 的单元测试类.
 *
 * @author blinkfox on 2019-08-16.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BlogRepositoryTest {

    @Resource
    private BlogRepository blogRepository;

    /**
     * 测试使用 {@link com.blinkfox.fenix.jpa.QueryFenix} 注解根据任意参数多条件模糊分页查询博客信息.
     */
    @Test
    public void queryMyBlogs() {
        // 构造查询的相关参数.
        List<String> ids = Arrays.asList("1", "2", "3", "4", "5", "6");
        Blog blog = new Blog().setAuthor("ZhangSan").setUpdateTime(new Date());
        Pageable pageable = PageRequest.of(0, 3, Sort.by(Sort.Order.desc("createTime")));

        // 查询并断言查询结果的正确性.
        Page<Blog> blogs = blogRepository.queryMyBlogs(ids, blog, pageable);
        Assert.assertEquals(4, blogs.getTotalElements());
        Assert.assertEquals(3, blogs.getContent().size());
    }

    /**
     * 测试使用 {@link QueryFenix} 注解来演示根据任意参数来多条件模糊查询博客信息.
     */
    @Test
    public void queryBlogsByTemplate() {
        // 构造查询的相关参数.
        List<String> ids = Arrays.asList("1", "2", "3", "4", "5", "6");
        Blog blog = new Blog().setAuthor("ZhangSan").setUpdateTime(new Date());

        // 查询并断言查询结果的正确性.
        List<Blog> blogs = blogRepository.queryBlogsByTemplate(ids, blog);
        Assert.assertEquals(4, blogs.size());
    }

    /**
     * 测试使用 {@link QueryFenix} 注解和 Java API 来拼接 SQL 的方式来查询博客信息.
     */
    @Test
    public void queryBlogsWithJava() {
        // 构造查询的相关参数.
        String[] ids = new String[]{"1", "2", "3", "4", "5", "6", "7", "8"};
        Blog blog = new Blog().setAuthor("ZhangSan");
        Date startTime = Date.from(LocalDateTime.of(2019, Month.APRIL, 8, 0, 0, 0)
                .atZone(ZoneId.systemDefault()).toInstant());
        Date endTime = Date.from(LocalDateTime.of(2019, Month.OCTOBER, 8, 0, 0, 0)
                .atZone(ZoneId.systemDefault()).toInstant());

        // 查询并断言查询结果的正确性.
        List<Blog> blogs = blogRepository.queryBlogsWithJava(blog, startTime, endTime, ids);
        Assert.assertEquals(3, blogs.size());
    }

}
