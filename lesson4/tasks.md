todo
- test in postgres
- docker
- docs with db screenshot

== docs ==
sharding
- t_user => id
- t_session => user_id так как должны быть в одной схеме

Problems
- ShardingSphere + Liquibase with multiple datasource => FAIL. Шардинг на одну ноду
- ShardingSphere + Hibernate => FAIL. Use MyBatis

Docs
- Common: https://www.digitalocean.com/community/tutorials/understanding-database-sharding
- ShardingSphere docs: https://shardingsphere.apache.org/document/current/en/features/sharding/concept/sharding/
- ShardingSphere config: https://shardingsphere.apache.org/document/current/en/manual/sharding-jdbc/configuration/config-yaml/