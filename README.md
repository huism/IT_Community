## IT问答社区

## 资料

* [spring官方文档](https://spring.io/guides)
* [elasticeaserch社区](https://elasticsearch.cn/)
* [bootstrap文档](https://v3.bootcss.com/)
* []()


## 工具

* [git](https://git-scm.com)


## 脚本

*创建user表*
```sql
create table USER
(
	ID int primary key auto_increment not null,
	ACCOUNT_ID VARCHAR(100),
	NAME VARCHAR(50),
	TOKEN CHAR(36),
	GMT_CREATE BIGINT,
	GMT_MODIFIED BIGINT,
);


```

```
mvn flyway:migrate

mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate
```
