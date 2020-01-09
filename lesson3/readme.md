# Урок 1 - Домашняя бухгалтерия
## Что надо сделать

Базовый проект
сделать простой проект на выбор:
1) чат платформу
2) интернет магазин
3) что-то свое по согласованию с преподавателем

Для базовой реализации достаточно регистрации, авторизации, базовых действий через CRUD

## Описание решения
Сервис предназначен для учета доходов и расходов, и вывода статистики по ним.

Также позволяет импортировать CSV данные из приложения MoneyPro (IOS) - https://money.pro/ru/iphone/

Давно хотел написать такой сервис. Я веду учет финансов уже 9 лет в MoneyPro, и естественно уже накопилось много данных. Но функциональности отчетов в этом приложении мне не хватает. И мой смысл этого приложения - сконвертировать данные MoneyPro в Postgres, где уже можно поиграться со статистикой. Ну и второе - написать ДЗ по курсу :-)

Пока есть только REST бэкенд

## Технологии
- Java 8 + Spring Boot 2 + Swagger
- Spring jpa
- h2/postgres

## Запуск
Варианты запуска

1) **docker-compose**:
```
docker-compose build
docker-compose up
```

2) **В IDE**: Запустить FinanceApplication. 

## API - кратко
- Swagger - http://localhost:8091/swagger-ui.html
- Api-docs: http://localhost:8091/v2/api-docs

После запуска приложения - автоматически запускается БД в памяти и создается пользователь с данными
- Логин - login
- Пароль - password

Чтобы кратко проверить:
1) Получаем сессию пользователя: auth/createSession
```
curl -X POST "http://localhost:8080/auth/createSession" -H "accept: application/json" -H "Content-Type: application/json" -d "{ \"login\": \"login\", \"passsword\": \"password\"}"
```
2) Передаем сессию в функцию main/getTransactions и видем список доходов/расходов
```
curl -X GET "http://localhost:8080/main/getTransactions?fromDate=2010-10-10&sessionId=1672516039827669681" -H "accept: application/json"
```

## Описание API

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
    - **POST /config/addAccount(sessionId, RUB/USD/EUR, name)** - добавления нового счета
    - **GET /config/getAccounts** - получение всех счетов
    - **POST /config/addCategory(sessionId, INCOME/EXPENSE, name)** - добавление новой статьи расходов
    - **GET /config/getCategories** - получение списка статей расходов
 - Basic operations - основные операции
    - **POST /main/addTransaction(accountName, categoryName, money, comment)** - добавление нового дохода/расхода
    - **POST /main/accountMoneyTransfer(fromAccount, toAccount, Money, comment)** - перевод данных между счетами (пока только в одной валюте)
    - **GET /main/getTransactions(sessionId, fromDate(2019-01-10))** - получение списка операций с указанной даты
    - **GET /main/getAccountStat(sessionId)** - получение статистики по счетам
   
#### Архитектура
![Архитектура](readme.md-arch.png)

    
## Структура БД

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

