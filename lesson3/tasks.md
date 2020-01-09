#### Lesson 3
- P1: Split on auth + logic - 4h
- P1: NEW service: GUI: Auth + Transaction list (Spring MVC) - 16h
    - auth ui + logic: 8h
    - transaction list ui: 8h

- P2: TBD: NEW service: GUI(настройки): курс валют + фиск внутренней логики + обновление (http://www.cbr.ru/scripts/XML_daily.asp?date_req=01/01/2020) - 8h
- P2: TBD: fix RUB->USD->RUB money exchange during import from money pro - 4h

- P3: TBD: GUI: export/import (https://www.callicoder.com/spring-boot-file-upload-download-rest-api-example/) - 16h
- P3: TBD: user custom currency management - 16h

### NEXT
- use json-schema for validation
- appService + REST unit tests
- Use code validator from KKM(Lenar)
- swagger alternative

- fix common sonar warnings
- use java money: https://www.baeldung.com/java-money-and-currency
- moneyPro import performance
- WADL: https://javattitude.wordpress.com/2014/05/26/wadl-generator-for-spring-rest/

- private Long transactionType; => pass dict to hibernate
- change default values for swagger - http://springfox.github.io/springfox/docs/snapshot/#q27
- RESEARCH: return List<CategoryResponse>
- RESEARCH: swagger @ApiParam doesn't pass to code. Use @PutMapping("/receipts/resend/{id}")
- actuator

### docs
https://www.petrikainulainen.net/spring-data-jpa-tutorial/
https://habr.com/ru/post/263033/
https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#projections
https://www.roytuts.com/spring-boot-data-jpa-left-right-inner-and-cross-join-examples-on-three-tables/
https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-external-config