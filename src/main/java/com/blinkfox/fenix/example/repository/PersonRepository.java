package com.blinkfox.fenix.example.repository;

import com.blinkfox.fenix.example.dto.PersonSearch;
import com.blinkfox.fenix.example.entity.Person;
import com.blinkfox.fenix.example.vo.PersonVo;
import com.blinkfox.fenix.example.vo.PersonVo2;
import com.blinkfox.fenix.jpa.QueryFenix;
import com.blinkfox.fenix.jpa.transformer.ColumnAnnotationTransformer;
import com.blinkfox.fenix.jpa.transformer.PrefixUnderscoreTransformer;
import com.blinkfox.fenix.specification.FenixJpaSpecificationExecutor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Person Repository.
 *
 * @author blinkfox on 2022-03-31.
 * @since 2.7.0
 */
@Repository
public interface PersonRepository extends JpaRepository<Person, String>, FenixJpaSpecificationExecutor<Person> {

    /**
     * 多条件模糊分页查询人员信息，并返回自定义 VO 对象信息.
     *
     * @param personSearch 人员查询条件对象
     * @param pageable JPA 分页排序参数
     * @return 自定义的人员信息实体类
     * @since v1.1.0
     */
    @QueryFenix(resultType = PersonVo.class, resultTransformer = PrefixUnderscoreTransformer.class, nativeQuery = true)
    Page<PersonVo> queryPersonVos(@Param("search") PersonSearch personSearch, Pageable pageable);

    /**
     * 多条件模糊分页查询人员信息，并返回自定义 VO 对象信息.
     *
     * @param personSearch 人员查询条件对象
     * @param pageable JPA 分页排序参数
     * @return 自定义的人员信息实体类
     * @since v1.1.0
     */
    @QueryFenix(resultType = PersonVo2.class, resultTransformer = ColumnAnnotationTransformer.class,
            nativeQuery = true)
    Page<PersonVo2> queryPersonVos2(@Param("search") PersonSearch personSearch, Pageable pageable);

}
