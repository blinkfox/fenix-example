package com.blinkfox.fenix.example.repository;

import com.blinkfox.fenix.example.entity.Book;
import com.blinkfox.fenix.jpa.FenixJpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 图书信息查询仓库.
 *
 * @author blinkfox on 2020-12-07.
 * @since v2.4.0
 */
@Repository
public interface BookRepository extends FenixJpaRepository<Book, String> {

}
