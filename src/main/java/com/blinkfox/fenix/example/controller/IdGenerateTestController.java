package com.blinkfox.fenix.example.controller;

import com.blinkfox.fenix.example.entity.idgenerate.SnowflakeIdTestEntity;
import com.blinkfox.fenix.example.repository.idgenerate.SnowflakeIdTestEntityRepository;
import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ID 生成器测试的 Controller.
 *
 * @author blinkfox on 2025-07-22
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/ids")
public class IdGenerateTestController {

    @Resource
    private SnowflakeIdTestEntityRepository snowflakeIdTestEntityRepository;

    /**
     * 测试保存数据的方法.
     *
     * @return 保存后的数据
     */
    @GetMapping("/snowflake/save")
    public Object snowflakeSave() {
        List<SnowflakeIdTestEntity> entities = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            SnowflakeIdTestEntity entity = new SnowflakeIdTestEntity();
            entity.setName("测试" + i);
            entities.add(this.snowflakeIdTestEntityRepository.save(entity));
        }
        return entities;
    }

    /**
     * 测试获取所有数据的方法.
     *
     * @return 获取数据
     */
    @GetMapping("/snowflake/get")
    public Object findAll() {
        return this.snowflakeIdTestEntityRepository.findAll();
    }

}
