# Домашняя бухгалтерия

## Описание
Сервис предназначен для учета доходов и расходов, и вывода статистики по ним.

Также позволяет импортировать CSV данные из приложения MoneyPro (IOS) - https://money.pro/ru/iphone/

Давно хотел написать такой сервис. Я веду учет финансов уже 9 лет в MoneyPro, и естественно уже накопилось много данных. Но функциональности отчетов в этом приложении мне не хватает. И мой смысл этого приложения - сконвертировать данные MoneyPro в Postgres, где уже можно поиграться со статистикой. Ну и второе - написать ДЗ по курсу :-)

Пока есть только REST бэкенд

## Технологии
- Java + Spring Boot 2 + Swagger
- Spring jpa
- h2/postgres

## Запуск
Для запуска нужны JAVA 11 + Docker

1) В Docker - при этом запускается приложения с БД в памяти(H2). И в него импортируются данные по пользователю: login/password
```
mvn clean install
docker build -t otus/finance-app .
docker run -p 8080:8080 -t otus/finance-app
```

2) Запустить FinanceApplication. Аналогично запустится приложения с БД в памяти(H2). И заимпортируются данные.

NOTE: Если нужно использовать Postgres вместо H2 - то нужно проставить профайл 'postgres' в application.yml и указать в нем ссылку на БД. При запуске приложения оно само сделает в нем таблицы и их заполнит.


## API
- Swagger - http://localhost:8080/swagger-ui.html
- Api-docs: http://localhost:8080/v2/api-docs

#### Сущности
- User - пользователь. Содержит логин и пароль
- Session - активные сессии для пользователей
- Account - счета. Например - наличка/кредитка, ...
- Category - статья расходов. Например - еда/автомобиль/аренда, .. У каждого счета есть тип - это доход или расход
- Transaction - собственно список доходов/расходов и переводов между счетами.

#### API поделен на 3 части
- User management - создание/проверка пользователя. И создание сессии.
    - **POST /auth/createUser(login, password)** - создание пользователя.
    - **POST /auth/checkUser(login,password)** - проверка наличия пользователя в БД.
    - **POST /auth/createSession(login,password)** - создание сессионного ключа. Он используется для авторизации в других запросах.
- App configuration - настройка приложения для пользователя
    - **POST /config/addAccount(sessionId, RUB/USD, name)** - добавления нового счета
    - **GET /config/getAccounts** - получение всех счетов
    - **POST /config/addCategory(sessionId, INCOME/EXPENSE, name)** - добавление новой статьи расходов
    - **GET /config/getCategories** - получение списка статей расходов
 - Basic operations - основные операции
    - **POST /main/addTransaction(accountName, categoryName, money, comment)** - добавление нового дохода/расхода
    - **POST /main/accountMoneyTransfer(fromAccount, toAccount, Money, comment)** - перевод данных между счетами (пока только в одной валюте)
    - **GET /main/getTransactions(sessionId, fromDate(2019-10-10))** - получение списка операций с указанной даты
    - **GET /main/getAccountStat(sessionId)** - получение статистики по счетам
    
## Структура БД

```
create database finance;
create user finance;
alter user finance with encrypted password 'finance';
grant all privileges on database finance to finance;
```


```
create table if not exists "user"
(
	id integer not null
	login varchar(100) not null,
	password varchar(100) not null
);

create table currency
(
	id integer not null
	name varchar(100),
	is_default boolean,
	rate numeric(16,2)
);

create table if not exists account
(
	id integer not null
	user_id integer,
	currency_id integer,
	name varchar(100),
	amount numeric(16,2)
);

create table if not exists category
(
	id integer not null
	user_id integer,
	transaction_type integer,
	name varchar(100)
);

create table if not exists session
(
	id integer not null
	user_id integer,
	session_id bigint,
	status integer
);

create table if not exists transaction
(
	id integer not null
	user_id integer,
	transaction_type integer,
	date date,
	account_id integer,
	amount numeric(16,2),
	to_account_id integer,
	to_amount numeric(16,2),
	category_id integer,
	comment varchar(100)
);
```

