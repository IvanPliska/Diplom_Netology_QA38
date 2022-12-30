# Дипломный проект по профессии «Тестировщик»

Дипломный проект — автоматизация тестирования комплексного сервиса, взаимодействующего с СУБД и API Банка.


## Процедура запуска авто тестов

1. Клонировать репозиторий:
   `git clone https://github.com/IvanPliska/Diplom_Netology_QA38.git`

2. Открыть папку `Diplom_QA38` в IntelliJ IDEA

3. Запустить контейнер Docker командой в консоли IntelliJ IDEA:
> `docker-compose up -d`

4. Запустить приложение командой в консоли IntelliJ IDEA:

*для MySQL*:
> `java "-Dspring.datasource.url=jdbc:mysql://localhost:3307/app" "-Dspring.datasource.username=app" "-Dspring.datasource.password=pass" -jar artifacts/aqa-shop.jar`

*для PostgreSQL*:
> `java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" "-Dspring.datasource.username=app" "-Dspring.datasource.password=pass" -jar artifacts/aqa-shop.jar`

5. Запустить авто-тесты командой в консоли

*для MySQL*:
> `./gradlew test "-Ddb.url=jdbc:mysql://localhost:3307/app" "-Ddb.username=app" "-Ddb.password=pass"`

*для PostgreSQL*:
> `./gradlew test "-Ddb.url=jdbc:postgresql://localhost:5432/app" "-Ddb.username=app" "-Ddb.password=pass"`

6. Формирование Allure отчёта
> `./gradlew allureReport` - формирование отчета

> `./gradlew allureServe` -отображение отчета

7. После выполнения всех тестов остановить docker контейнер командой в консоли:
   `docker-compose down`