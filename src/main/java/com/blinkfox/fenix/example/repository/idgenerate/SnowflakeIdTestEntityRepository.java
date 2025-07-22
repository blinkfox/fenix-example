package com.blinkfox.fenix.example.repository.idgenerate;

import com.blinkfox.fenix.example.entity.idgenerate.SnowflakeIdTestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * {@link SnowflakeIdTestEntity} 对应的 Repository.
 *
 * @author blinkfox on 2025-07-22
 * @since 1.0.0
 */
public interface SnowflakeIdTestEntityRepository extends JpaRepository<SnowflakeIdTestEntity, Long> {
}
