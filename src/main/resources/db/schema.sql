-- 创建数据库表所在的模式 schema 为 test.
CREATE SCHEMA test;
commit;

-- 在 test 模式下创建数据库表.
DROP TABLE IF EXISTS test.t_blog;
CREATE TABLE test.t_blog (
    c_id varchar(32) NOT NULL,
    c_user_id varchar(255),
    c_author varchar(255),
    c_title varchar(255),
    c_content varchar(255),
    dt_create_time timestamp(6) NULL,
    dt_update_time timestamp(6) NULL,
    constraint pk_test_blog primary key(c_id)
);
commit;
