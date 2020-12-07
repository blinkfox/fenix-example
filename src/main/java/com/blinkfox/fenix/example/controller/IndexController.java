package com.blinkfox.fenix.example.controller;

import com.blinkfox.fenix.example.entity.Blog;
import com.blinkfox.fenix.example.repository.BlogRepository;
import com.blinkfox.fenix.id.IdWorker;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * IndexController.
 *
 * @author blinkfox on 2019-08-31.
 */
@Slf4j
@RestController
@RequestMapping
public class IndexController {

    private static final IdWorker idWorker = new IdWorker();

    @Resource
    private BlogRepository blogRepository;

    /**
     * say Hello.
     *
     * @return hello
     */
    @GetMapping
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello World!");
    }

    /**
     * 查询所有博客信息.
     *
     * @return hello
     */
    @GetMapping("/blogs")
    public ResponseEntity<List<Blog>> findBlogs() {
        // 构造查询的相关参数.
        List<String> ids = Arrays.asList("1", "2", "3", "4", "5", "6");
        Blog blog = new Blog().setAuthor("ZhangSan").setUpdateTime(new Date());

        // 查询并返回结果集.
        List<Blog> blogs = blogRepository.queryBlogsByTemplate(ids, blog);
        return ResponseEntity.ok(blogs);
    }

}
