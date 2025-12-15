# My Market App

Spring Boot приложение для интернет-магазина с использованием JPA, Thymeleaf и PostgreSQL.

## Требования
- Java 21
- Maven 3.9+
- Локальная база данных PostgreSQL (по умолчанию `jdbc:postgresql://localhost:5432/mymarket`, пользователь `ceeler`, пароль `root` из профиля `dev`).

## Запуск в среде разработки
1. Убедитесь, что PostgreSQL доступен по настройкам из `src/main/resources/application-dev.yaml` или измените их под свою среду.
2. Активируйте профиль `dev` (он включён по умолчанию в `application.yaml`) и запустите приложение:
   ```bash
   mvn spring-boot:run
   ```
3. Приложение будет доступно на `http://localhost:8080`.

## Локальная сборка и запуск
1. Соберите исполняемый JAR (тесты можно пропустить флагом `-DskipTests`):
   ```bash
   mvn clean package -DskipTests
   ```
2. Запустите артефакт с нужным профилем:
   ```bash
   java -jar target/my-market-app-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev
   ```

## Тестирование
Запуск модульных и интеграционных тестов:
```bash
mvn test
```

## Сборка и запуск в Docker
1. Соберите образ (используется многослойная сборка Maven + JRE):
   ```bash
   mvn clean package
   docker build -t my-market-app .
   ```
2. Запустите контейнер. По умолчанию активируется профиль `ci`, который берёт параметры подключения к БД из переменных среды:
   ```bash
   docker run --rm -p 8080:8080 \
     -e SPRING_PROFILES_ACTIVE=ci \
     -e DB_URL=jdbc:postgresql://<host>:5432/mymarket \
     -e DB_USER=<username> \
     -e DB_PASS=<password> \
     my-market-app
   ```
   Если база данных и приложение работают на одной машине, задайте `<host>` как `host.docker.internal` (для Docker Desktop) или IP адрес машины.

Образ запускает приложение на порту `8080` внутри контейнера; он проброшен в примере на тот же порт хоста.
