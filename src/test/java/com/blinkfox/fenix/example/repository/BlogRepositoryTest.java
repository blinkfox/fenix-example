package com.blinkfox.fenix.example.repository;

import com.blinkfox.fenix.example.dto.BlogDto;
import com.blinkfox.fenix.example.entity.Blog;
import com.blinkfox.fenix.example.vo.BlogParam;
import com.blinkfox.fenix.jpa.QueryFenix;
import com.blinkfox.fenix.specification.FenixSpecification;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
     * 测试使用 {@link QueryFenix} 注解根据任意参数多条件模糊分页查询博客信息.
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
        List<Blog> blogs = blogRepository.queryBlogsByTemplate(ids,
                new Blog().setAuthor("ZhangSan").setUpdateTime(new Date()));
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

    /**
     * 测试使用 {@link QueryFenix} 注解来演示根据任意参数来多条件模糊查询博客信息，并返回一个自定义的实体对象.
     *
     * @since v1.1.0
     */
    @Test
    public void queryBlogDtos() {
        // 根据参数查询，并断言查询结果的正确性.
        Page<BlogDto> blogPage = blogRepository.queryBlogDtos(Arrays.asList("1", "2", "3", "4"), "Spring",
                PageRequest.of(0, 2, Sort.by(Sort.Order.desc("createTime"))));
        Assert.assertEquals(3, blogPage.getTotalElements());
        Assert.assertNotNull(blogPage.getContent().get(0).getTitle());
    }

    /**
     * 测试使用 Fenix 中的  {@link FenixSpecification} 的链式 Java API 来动态查询博客信息.
     */
    @Test
    public void queryBlogsWithSpecifition() {
        // 模拟构造查询的相关 map 型参数.
        Map<String, Object> params = new HashMap<>();
        params.put("ids", new String[]{"1", "2", "3", "4", "5", "6", "7", "8"});
        params.put("author", "ZhangSan");
        params.put("startTime", Date.from(LocalDateTime.of(2019, Month.APRIL, 8, 0, 0, 0)
                .atZone(ZoneId.systemDefault()).toInstant()));
        params.put("endTime", Date.from(LocalDateTime.of(2019, Month.OCTOBER, 8, 0, 0, 0)
                .atZone(ZoneId.systemDefault()).toInstant()));

        // 开始查询，并验证正确性.
        Object[] ids = (Object[]) params.get("ids");
        List<Blog> blogs = blogRepository.findAll(FenixSpecification.of(builder ->
                builder.andIn("id", ids, ids != null && ids.length > 0)
                        .andLike("title", params.get("title"), params.get("title") != null)
                        .andLike("author", params.get("author"))
                        .andBetween("createTime", params.get("startTime"), params.get("endTime"))
                .build()));
        Assert.assertEquals(3, blogs.size());
        blogs.forEach(blog -> Assert.assertTrue(blog.getAuthor().endsWith("ZhangSan")));
    }

    /**
     * 测试使用 Fenix 中的  {@link FenixSpecification} 的链式 Java API 来动态查询博客信息.
     */
    @Test
    public void queryBlogsWithAnnotaion() {
        Date startTime = Date.from(LocalDateTime.of(2019, Month.APRIL, 8, 0, 0, 0)
                .atZone(ZoneId.systemDefault()).toInstant());
        Date endTime = Date.from(LocalDateTime.of(2019, Month.OCTOBER, 8, 0, 0, 0)
                .atZone(ZoneId.systemDefault()).toInstant());
        BlogParam blogParam = new BlogParam()
                .setIds(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8"))
                .setAuthor("ZhangSan")
                .setCreateTime(new Date[]{startTime, endTime});

        // 开始查询，并验证正确性.
        List<Blog> blogs = blogRepository.findAll(FenixSpecification.ofBean(blogParam));
        Assert.assertEquals(3, blogs.size());
        blogs.forEach(blog -> Assert.assertTrue(blog.getAuthor().endsWith("ZhangSan")));
    }

}
