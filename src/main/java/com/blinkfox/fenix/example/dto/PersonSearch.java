package com.blinkfox.fenix.example.dto;

import com.blinkfox.fenix.specification.annotation.Between;
import com.blinkfox.fenix.specification.annotation.Equals;
import com.blinkfox.fenix.specification.annotation.In;
import com.blinkfox.fenix.specification.annotation.Like;
import com.blinkfox.fenix.specification.handler.bean.BetweenValue;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Person 的查询对象.
 *
 * @author blinkfox on 2022-03-31.
 * @since 2.7.0
 */
@Getter
@Setter
@Accessors(chain = true)
public class PersonSearch {

    /**
     * 根据 ID 等值查询的条件，如果该值不为 null 就进行等值查询.
     */
    @Equals
    private String id;

    /**
     * 根据昵称模糊查询的条件，如果该值不为 null 就进行模糊查询.
     */
    @Like
    private String nickName;

    /**
     * 根据状态 IN 范围查询的条件，如果该值不为空就进行 in 范围查询.
     */
    @In("status")
    private List<Integer> statusList;

    /**
     * 根据年龄区间查询的条件，如果该值不为空就进行 Between and 查询，
     * 或者会根据边界值是否为空，自动退化为大于等于或者小于等于的查询.
     */
    @Between
    private BetweenValue<Integer> age;

}
