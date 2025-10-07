# Автоматизация тестирования сайта [Издательства АЗБУКА](https://azbooka.ru)

![Azbooka Banner](/assets/img/azbooka_banner.png)

## Содержание

* [Описание](#описание)
* [Технологии и инструменты](#технологии-и-инструменты)
* [Реализованные проверки](#реализованные-проверки)
* [Запуск тестов](#запуск-тестов)
  * [Локальная конфигурация](#локальная-конфигурация)
* [Сборка в Jenkins](#сборка-в-jenkins)
* [Интеграция с Allure Report](#интеграция-с-allure-report)
* [Интеграция с Allure TestOps](#интеграция-с-allure-testops)
* [Уведомления в Telegram](#уведомления-в-telegram)
* [Видео выполнения теста](#видео-выполнения-теста)
* [Правовая информация](#правовая-информация)

## Описание

**Издательство АЗБУКА** — одно из крупнейших издательств в России. Ежегодно выпускает более 100 книжных серий и отдельных проектов. В издательство входят четыре импринта: «Азбука», «Иностранка», «КоЛибри» и «Махаон».

### Особенности проекта

* **Современная архитектура тестов**
    * Использован шаблон проектирования *Page Object* для поддержки чистоты и переиспользуемости кода;
    * Конфигурации написаны с использованием библиотеки [Owner](https://github.com/matteobaccan/owner), что обеспечивает гибкость и удобство настройки окружений.

* **Генерация данных и модели**
  * Библиотека [Datafaker](https://github.com/datafaker-net/datafaker) используется для создания тестовых данных "на лету";
  * Для описания API-моделей применен [Lombok](https://github.com/projectlombok/lombok), что позволило сократить шаблонный код.

* **Кастомные расширения для тестов**
  * `@WithLogin` — автоматическая авторизация в UI-тестах;
  * `@Layer`, `@Microservice`, `@Manual` — категоризация тестов в Allure TestOps.

* **Удобные сценарии запуска**
    * Локальный и удалённый запуск;
    * Фильтрация тестов по тегам;
    * Возможность запуска напрямую из Allure TestOps.

* **CI/CD-процессы**
  * При каждом пуше в основные ветки и перед мержем пулл-реквестов запускается сборка проекта в GitHub Actions.

* **Интеграция с Allure**
    * После прохождения автотестов формируется наглядный Allure-отчёт:
        * Шаги выполнения;
        * Скриншот страницы перед завершением теста;
        * Видео прохождения сценария;
        * HTML-код страницы и логи браузера.
    * Результаты автоматически синхронизируются с Allure TestOps.

* **Уведомления о результатах**
    * Итоги тестового прогона отправляются в Telegram-чат с помощью библиотеки [Allure notifications](https://github.com/qa-guru/allure-notifications).

## Технологии и инструменты

<p align="center">
<a href="https://www.jetbrains.com/idea/"><img src="/assets/img/logo/intellij-idea-badge.svg" alt="IntelliJ IDEA" style="width:5%;"></a>
<a href="https://www.java.com/ru/"><img src="/assets/img/logo/java-badge.svg" alt="Java" style="width:5%;"></a>
<a href="https://junit.org/junit5/"><img src="/assets/img/logo/junit5-badge.svg" alt="JUnit5" style="width:5%;"></a>
<a href="https://selenide.org/"><img src="/assets/img/logo/selenide-badge.svg" alt="Selenide" style="width:5%;"></a>
<a href="https://rest-assured.io/"><img src="/assets/img/logo/rest-assured-badge.svg" alt="REST Assured" style="width:5%;"></a>
<a href="https://gradle.org//"><img src="/assets/img/logo/gradle-badge.svg" alt="Gradle" style="width:5%;"></a>
<a href="https://git-scm.com/"><img src="/assets/img/logo/git-badge.svg" alt="Git" style="width:5%;"></a>
<a href="https://github.com/"><img src="/assets/img/logo/github-badge.svg" alt="GitHub" style="width:5%;"></a>
<a href="https://www.jenkins.io/"><img src="/assets/img/logo/jenkins-badge.svg" alt="Jenkins" style="width:5%;"></a>
<a href="https://aerokube.com/selenoid/"><img src="/assets/img/logo/selenoid-badge.svg" alt="Selenoid" style="width:5%;"></a>
<a href="https://allurereport.org/"><img src="/assets/img/logo/allure-report-badge.svg" alt="Allure Report" style="width:5%;"></a>
<a href="https://qameta.io/"><img src="/assets/img/logo/allure-testops-badge.svg" alt="Allure TestOps" style="width:5%;"></a>
<a href="https://telegram.org/"><img src="/assets/img/logo/telegram-badge.svg" alt="Telegram" style="width:5%;"></a>
</p>

* **Среда разработки**
  * [Intellij IDEA](https://www.jetbrains.com/idea/)

* **Язык программирования**
  * [Java](https://www.java.com/)

* **Фреймворки для тестирования**
  * [JUnit 5](https://junit.org/) — современный тестовый фреймворк с поддержкой аннотаций, параметризации и расширений
  * [Selenide](https://selenide.org/) — лаконичная обёртка над Selenium WebDriver для UI-тестов
  * [REST Assured](https://rest-assured.io/) — декларативное тестирование REST API

* **Сборка и управление зависимостями**
  * [Gradle](https://gradle.org) — сборка, управление зависимостями и настройка запуска тестов

* **Контроль версий и репозиторий**
  * [Git](https://git-scm.com/) — система контроля версий
  * [GitHub](https://github.com/) — хостинг и управление проектом

* **Инфраструктура и CI/CD**
  * [GitHub Actions](https://github.com/features/actions) — автоматизация рабочих процессов и CI/CD в GitHub
  * [Jenkins](https://www.jenkins.io/) — автоматизация прогонов тестов, формирование отчётов и интеграция с внешними сервисами
  * [Selenoid](https://aerokube.com/selenoid/) — лёгкая и быстрая инфраструктура для параллельного запуска браузеров в Docker

* **Отчётность и интеграции**
  * [Allure Report](https://allurereport.org/) — детализированные отчёты о тестовых прогонах
  * [Allure TestOps](https://qameta.io/testops/) — управление тестами и аналитика результатов
  * [Telegram](https://core.telegram.org/bots) — интеграция через бота для оперативных уведомлений о статусе прогонов

## Реализованные проверки

<details>
   <summary>API</summary>

**Авторизация**
- [x] Авторизация с валидными данными через API
- [x] Авторизация с несуществующим логином через API
- [x] Авторизация с неправильным паролем через API
- [x] Авторизация с пустым логином
- [x] Авторизация с пустым паролем
- [x] Авторизация с отсутствующим логином
- [x] Авторизация с отсутствующим паролем
- [x] Авторизация с отсутствующим телом запроса
- [x] Выход из аккаунта с валидным токеном
- [x] Выход из аккаунта с невалидным токеном
- [x] Выход из аккаунта без токена

**Профиль пользователя**
- [x] Получение информации о текущем пользователе
- [x] Изменение информации о текущем пользователе без загрузки аватара
- [x] Загрузка аватара пользователя с разными расширениями файлов

**Закладки**
- [x] Получение пустого списка закладок
- [x] Получение не пустого списка закладок
- [x] Добавление книги в закладки через API, когда список закладок пустой
- [x] Добавление книги в закладки через API, когда список закладок не пустой
- [x] Удаление книги из закладок через API, когда в списке закладок только одна книга
- [x] Удаление книги из закладок через API, когда в списке закладок несколько книг
</details>

<details>
   <summary>UI</summary>

**Авторизация**
- [x] Авторизация с валидными данными
- [x] Авторизация с несуществующим логином
- [x] Авторизация с неправильным паролем
- [x] Авторизация без заполнения полей
- [x] Выход из аккаунта
- [x] Переход на страницу профиля после выхода из аккаунта

**Профиль пользователя**
- [x] Отображение информации в профиле с загруженным аватаром
- [x] Изменение информации в профиле с загрузкой аватара

**Закладки**
- [x] Добавление книги в закладки, когда список закладок пустой, с переходом на страницу "Закладки"
- [x] Добавление книги в закладки, когда список закладок не пустой
- [x] Удаление книги из закладок на странице книги, когда в списке закладок только одна книга, с переходом на страницу "Закладки"
- [x] Удаление книги из закладок на странице книги, когда в списке закладок несколько книг
- [x] Удаление книги из закладок на странице "Закладки", когда в списке закладок только одна книга
- [x] Удаление книги из закладок на странице "Закладки", когда в списке закладок несколько книг
- [x] Отображение книг в списке закладок
- [x] Отображение счетчика закладок
- [x] Переход к закладкам через ссылку в шапке
- [x] Счетчик закладок не отображается, когда нет закладок
- [x] Счетчик закладок отображается, когда есть закладки
</details>

<details>
   <summary>Ручные проверки</summary>

**Поиск**
- [x] Дропдаун поиска отображает книги в блоке "Книги"
- [x] Дропдаун поиска отображает статьи в блоке "Статьи"
- [x] Дропдаун поиска показывает сообщение, если нет результатов
- [x] Переход на страницу поиска по нажатию на клавишу Enter
- [x] Переход на страницу поиска по кнопке "Показать еще"
- [x] Нажатие на книгу в дропдауне открывает страницу книги
</details>

## Запуск тестов

```mermaid
---
config:
  theme: base
  themeVariables:
    primaryColor: '#e6e6e6'
    primaryBorderColor: '#c41325'
    primaryTextColor: '#c41325'
    lineColor: '#c41325'
    nodeBorder: '#c41325'
---
flowchart LR
 subgraph C["Локальный запуск"]
        C1["Запуск на локальной машине"]
  end
 subgraph D["Удалённый запуск"]
        D2[/"Учетные данные Selenoid"/]
        D1["Запуск в Selenoid"]
  end
 subgraph S["Настройки запуска"]
        S1[/"Параметры браузера"/]
        S2[/"Учетные данные тестового пользователя"/]
  end
    A(["Запуск тестов"]) --> B{"Выбор окружения<br>remote=?"}
    B -- false --> C1
    B -- true --> D1
    D1 --> D2
    C1 --> S1
    D2 --> S1
    S1 --> S2
    S2 --> U{"Выбор типов тестов<br>includeTags=?"}
    U -- ui --> V["UI-тесты"]
    U -- api --> W["API-тесты"]
    U -- regress --> X["Регрессионные тесты"]
    U -- smoke --> Y["Smoke-тесты"]
    V --> Z(["Результаты тестов"])
    W --> Z
    X --> Z
    Y --> Z
    N1["Возможно хранение в конфигурационных файлах"] -.-> D2 & S1 & S2
    N2["Допускается комбинирование"] -.-> U
    N1@{ shape: braces}
    N2@{ shape: braces}
     A:::terminator
     B:::decision
     C:::block
     D:::block
     S:::block
     U:::decision
     Z:::terminator
    classDef terminator fill:#f5b7b1,stroke:#c0392b,stroke-width:2px,color:#111
    classDef decision fill:#c41325,stroke:#922b21,stroke-width:2px,color:#f5f5f5
    classDef block fill:#d6d6d6,stroke:#ccc,stroke-width:2px,color:#333
```

```bash
gradle clean test -Premote={true|false} -PincludeTags={tags} \
  -Dremote.domain={domain} -Dremote.username={username} -Dremote.password={password} \
  -Dbrowser.name={name} -Dbrowser.version={version} -Dbrowser.size={size} \
  -Dtest.email={email} -Dtest.password={password}
```

- `remote` — выбор окружения (опционально):
  - `true` — запуск тестов в Selenoid
  - `false` — запуск тестов локально (по умолчанию)
- `groups` — теги тестов для запуска:
    - `ui` — UI-тесты
    - `api` — API-тесты
    - `regress` — регрессионные тесты
    - `smoke` — smoke-тесты
- `remote.*` — данные для авторизации на удалённом стенде:
    - `username`
    - `password`
- `browser.*` — параметры браузера для UI тестов: название, версия, размер окна
    - `name` (по умолчанию `chrome`)
    - `version`
    - `size` (по умолчанию `1920x1080`)
- `test.*` — логин и пароль для авторизации тестовым пользователем в тестах:
    - `email`
    - `password`

<details>
   <summary>Дополнительные команды</summary>

1. Формирование Allure-отчёта:
   ```bash
   gradle allureReport
   ```
   По умолчанию отчёт генерируется в папку `build/allure-results`.

2. Формирование Allure-отчёта, запуск локального HTTP-сервера и открытие в браузере:
   ```bash
   gradle allureServe
   ```
</details>

### Локальная конфигурация

Для упрощения локального запуска можно создать файлы `remote.properties` и `user.properties` в корневой папке `config`.

В папке есть шаблоны: [remote.example.properties](/config/remote.example.properties) и [user.example.properties](/config/user.example.properties). Необходимо скопировать их, убрав из имени `.example`, и заполнить своими данными.

Минимальный набор параметров для локального запуска, если настроена локальная конфигурация:
```bash
gradle clean test
```

## Сборка в [Jenkins](https://jenkins.autotests.cloud/job/C36-NeverFlyDog-Azbooka/)

[Jenkins](https://www.jenkins.io/) — инструмент для автоматизации сборки, тестирования и деплоя проектов.

В проекте используется для:
* удалённого запуска автотестов,
* формирования Allure-отчётов по результатам прогонов,
* интеграции с уведомлениями в Telegram.

На странице проекта доступна информация о последних сборках и график успешности прохождения тестов из Allure-отчёта:

<img src="/assets/img/screenshots/jenkins_1.png" alt="Jenkins - Project Overview">

Раздел `Build with Parameters` позволяет задать необходимые параметры перед запуском сборки:

<img src="/assets/img/screenshots/jenkins_2.png" alt="Jenkins - Build with Parameters">

После завершения сборки автоматически формируется Allure-отчёт, и обновляются результаты прохождения тестов в Allure TestOps. 

## Интеграция с [Allure Report](https://jenkins.autotests.cloud/job/C36-NeverFlyDog-Azbooka/allure/)

[Allure Report](https://allure.qatools.ru/) — инструмент для визуализации результатов тестов, который формирует наглядные и подробные отчёты.

В проекте используется для:
* отображения шагов выполнения теста,
* демонстрации скриншотов и видео,
* анализа логов и исходного кода страницы,
* отслеживания успешных и упавших тестов.

Во вкладке `Overview` отображается общий результат прогона тестов: диаграммы успешных и упавших тестов, распределение по сьютам и функциональным фичам:

<img src="/assets/img/screenshots/allure_report_1.png" alt="Allure Overview">

При переходе в конкретный тест доступны подробности — шаги теста, скриншоты, видео и логи браузера:

<img src="/assets/img/screenshots/allure_report_2.png" alt="Allure Test Details">

## Интеграция с [Allure TestOps](https://allure.autotests.cloud/project/4941/)

[Allure TestOps](https://qameta.io/testops/) — облачная платформа для управления тестами и анализа результатов.

В проекте используется для:
* хранения тест-кейсов,
* группировки тестов по функциональностям и пользовательским историям,
* отслеживания выполнения тестов в динамике,
* интеграции с Jenkins и отчётами Allure.

Интеграция с Jenkins позволяет запускать выбранные тесты и отслеживать их выполнение в реальном времени:

<img src="/assets/img/screenshots/allure_testops_1.png" alt="Allure TestOps - Jenkins Integration">

Прогоны тестов отображаются на графиках и диаграммах, что позволяет быстро оценивать динамику и стабильность тестов:

<img src="/assets/img/screenshots/allure_testops_2.png" alt="Allure TestOps - Test Runs">

Тестовые сценарии автоматически синхронизируются с кодом проекта после завершения прогонов, обеспечивая актуальность кейсов:

<img src="/assets/img/screenshots/allure_testops_3.png" alt="Allure TestOps Test Cases">

## Уведомления в Telegram

Результаты тестового прогона автоматически отправляются в Telegram-чат, что позволяет оперативно отслеживать статус прогонов и быстро реагировать на падения тестов:

<img src="/assets/img/screenshots/allure_notifications_1.png" alt="Allure Notifications - Telegram">

## Видео выполнения теста

Для каждого UI-теста в отчёте прилагается видео прохождения, позволяя визуально проверить выполнение шагов и воспроизвести возможные ошибки:

<img src="/assets/video/test.gif" alt="Selenoid Video">

## Правовая информация

Все названия продуктов, логотипы и изображения (включая обложки книг и материалы сайта azbooka.ru, которые используются в тестах) использованы исключительно в образовательных и демонстрационных целях. Все права принадлежат их законным владельцам.
