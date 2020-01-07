# Урок 2 - Сервис уведомлений
## Что надо сделать
Необходимо создать 2 сервиса для рассылки уведомлений (email и push, например). Сделать над ними универсальный интерфейс

## Технологии и запуск
Технологии: Java 8 + Spring Boot 2 + Swagger + ActiveMQ


Варианты запуска

1) **docker-compose**:
```
docker-compose build
docker-compose up
```

2) **В IDE**: Запустить CentralApplication, EmailApplication, SmsApplication


## Описание решения
Сервис предназначен для рассылки уведомлений.
Есть входная точка(REST) и отдельные микросервисы для рассылки СМС и  Email. Общение между ними идет через очередь (ActiveMQ)

Микросервисы:
* CentralService - входная точка. REST сервис.  
* EmailService - сервис для рассылки почтовых уведомлений
* SmsService -  сервис для рассылки СМС

![Архитектура](readme.md-arch.png)



//TODO: логика работы
//TODO: описать текушие сервисы и как они взаимодействуют (конфиги)
//TODO: как добавлять новые сервисы
//TODO: image
//TODO: получение статуса

## Пример вызова

swagger - http://localhost:8080/swagger-ui.html
amq admin: http://localhost:8161/admin admin/admin




