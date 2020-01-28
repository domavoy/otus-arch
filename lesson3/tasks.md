### NEXT
- GUI: Auth + Transaction list (Spring MVC), Configurations
- GUI: export/import (https://www.callicoder.com/spring-boot-file-upload-download-rest-api-example/)
- use json-schema for validation
- appService + REST unit tests
- Use code validator from KKM(Lenar)
- swagger alternative
- GOOGLE: best practices - spring boot contract: https://aboullaite.me/a-practical-introduction-to-spring-cloud-contract/
- GOOGLE: best practices - how to pass dict to rest. now - string and validate using dict in persistence
- swagger on oauth 3
- fix RUB->USD->RUB money exchange during import from money pro
- user custom currency management
- grafana/esia
- move all dependencies to root pom
- gradle ?
- liquibase test with postgres as lenar
- one swagger for all - https://github.com/GnanaJeyam/microservice-patterns
- generate code coverage ...., check style ...
- auth-service REST + auth-service-client CLIENT versioning ? other services ?

- feature: export/import all data: dict + repeatedPayment + budget. now - transactions only
- thinks on common-services
- think entity naming: api(request/reponse)/entity

- fix common sonar warnings
- use java money: https://www.baeldung.com/java-money-and-currency
- moneyPro import performance
- WADL: https://javattitude.wordpress.com/2014/05/26/wadl-generator-for-spring-rest/

- private Long transactionType; => pass dict to hibernate
- change default values for swagger - http://springfox.github.io/springfox/docs/snapshot/#q27
- RESEARCH: return List<CategoryResponse>
- RESEARCH: swagger @ApiParam doesn't pass to code. Use @PutMapping("/receipts/resend/{id}")
- actuator
- FEATURE: repeated-service => getPaymentData for specific granularity
- gRPC/thrift for internal communication

### docs
https://www.petrikainulainen.net/spring-data-jpa-tutorial/
https://habr.com/ru/post/263033/
https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#projections
https://www.roytuts.com/spring-boot-data-jpa-left-right-inner-and-cross-join-examples-on-three-tables/
https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-external-config