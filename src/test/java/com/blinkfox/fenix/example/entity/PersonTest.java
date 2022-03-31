package com.blinkfox.fenix.example.entity;

import com.blinkfox.fenix.example.dto.PersonSearch;
import com.blinkfox.fenix.example.repository.PersonRepository;
import com.blinkfox.fenix.example.vo.PersonVo;
import com.blinkfox.fenix.example.vo.PersonVo2;
import com.blinkfox.fenix.helper.StringHelper;
import com.blinkfox.fenix.specification.handler.bean.BetweenValue;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * {@link Person} 的单元测试类.
 *
 * @author blinkfox on 2022-03-31
 * @since v2.7.0
 */
@Slf4j
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PersonTest {

    private static final int COUNT = 9;

    private static final int YEAR = 2022;

    private static final int MONTH = 3;

    private static final String ACCOUNT = "person-account";

    private static final String NICK_NAME = "nick-name";

    private static PersonSearch search;

    private static Pageable pageable;

    private static Pageable pageable2;

    @BeforeAll
    void init() {
        List<Person> personList = new ArrayList<>();
        for (int i = 1; i <= COUNT; ++i) {
            personList.add(new Person()
                    .setAccount(ACCOUNT + "-" + i)
                    .setNickName(NICK_NAME + "-" + i)
                    .setAge(20 + i)
                    .setBirthday(LocalDate.of(YEAR, MONTH, i))
                    .setStatus(i % 4)
                    .setCreateTime(new Date())
                    .setLastUpdateTime(LocalDateTime.now()));
        }

        // 保存数据，并断言是否插入成功.
        PersonRepository personRepository = new Person().getRepository();
        personRepository.saveAll(personList);
        Assertions.assertEquals(personRepository.count(), COUNT);

        // 构造一些初始查询条件.
        search = new PersonSearch()
                .setNickName(NICK_NAME)
                .setStatusList(Lists.list(1, 2))
                .setAge(BetweenValue.of(21, 27));
        pageable = PageRequest.of(0, 3, Sort.by(Sort.Order.desc("birthday")));
        pageable2 = PageRequest.of(0, 2, Sort.by(Sort.Order.desc("d_birthday")));
    }

    @Test
    void save() {
        int num = 20;
        String account = ACCOUNT + "-" + num;
        int age = 20 + num;
        Person person = new Person()
                .setAccount(account)
                .setNickName(NICK_NAME + "-" + num)
                .setAge(age)
                .setBirthday(LocalDate.of(YEAR, MONTH, num))
                .setStatus(2)
                .setCreateTime(new Date())
                .setLastUpdateTime(LocalDateTime.now())
                .save();

        // 断言上面保存成功了.
        Optional<Person> personOption = person.findById();
        Assertions.assertTrue(personOption.isPresent());
        Person newPerson = personOption.get();
        Assertions.assertEquals(newPerson.getAccount(), account);
        Assertions.assertEquals(newPerson.getAge(), age);
        Assertions.assertEquals(newPerson.getNickName(), person.getNickName());
        Assertions.assertEquals(newPerson.getBirthday(), person.getBirthday());

        // 删除，并断言其不存在.
        person.delete();
        Assertions.assertFalse(person.findById().isPresent());
    }

    @Test
    void findAllPaging() {
        Page<Person> personPage = new Person().findAll(builder -> builder
                .andLike("nickName", search.getNickName(), StringHelper.isNotBlank(search.getNickName()))
                .andIn("status", search.getStatusList())
                .andBetween("age", search.getAge().getStart(), search.getAge().getEnd())
                .build(), pageable);

        this.doAssertPersonPaging(personPage);
    }

    @Test
    void findAllOfBeanPaging() {
        Page<Person> personPage = new Person().findAllOfBean(search, pageable);
        this.doAssertPersonPaging(personPage);
    }

    private void doAssertPersonPaging(Page<Person> personPage) {
        Assertions.assertEquals(personPage.getTotalElements(), 4);
        personPage.getContent().forEach(person -> {
            Assertions.assertNotNull(person.getId());
            Assertions.assertTrue(person.getAccount().startsWith(ACCOUNT));
            Assertions.assertTrue(person.getNickName().startsWith(NICK_NAME));
            Assertions.assertTrue(person.getStatus() == 1 || person.getStatus() == 2);
        });
    }

    @Test
    void findPagingWithResultType() {
        Page<PersonVo> personVoPage = new Person().getRepository().queryPersonVos(search, pageable2);

        Assertions.assertEquals(personVoPage.getTotalElements(), 4);
        personVoPage.getContent().forEach(person -> {
            Assertions.assertNotNull(person.getId());
            Assertions.assertTrue(person.getAccount().startsWith(ACCOUNT));
            Assertions.assertTrue(person.getNickName().startsWith(NICK_NAME));
            Assertions.assertTrue(person.getStatus() == 1 || person.getStatus() == 2);
        });
    }

    @Test
    void findPagingWithResultType2() {
        Page<PersonVo2> personVoPage2 = new Person().getRepository().queryPersonVos2(search, pageable2);

        Assertions.assertEquals(personVoPage2.getTotalElements(), 4);
        personVoPage2.getContent().forEach(person -> {
            Assertions.assertNotNull(person.getId());
            Assertions.assertTrue(person.getNickName().startsWith(NICK_NAME));
            Assertions.assertTrue(person.getStatus() == 1 || person.getStatus() == 2);
        });
    }

}