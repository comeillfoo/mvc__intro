# mvc__intro
The second problem of web-programming. The task is about of creating a MVC system that returns HTML-page with form that checks points hitting.

## Вариант: 2531
---

Разработать веб-приложение на базе сервлетов и JSP, определяющее попадание точки на координатной плоскости в заданную область.

![Task Area](https://github.com/Come1LLF00/mvc__intro/blob/master/areas.png "Area")

Приложение должно быть реализовано в соответствии с [шаблоном MVC](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93controller) и состоять из следующих элементов:

+ **ControllerServlet**, определяющий тип запроса, и, в зависимости от того, содержит ли запрос информацию о координатах точки и радиусе, делегирующий его обработку одному из перечисленных ниже компонентов. Все запросы внутри приложения должны передаваться этому сервлету (по методу GET или POST в зависимости от варианта задания), остальные сервлеты с веб-страниц напрямую вызываться не должны.
+ **AreaCheckServlet**, осуществляющий проверку попадания точки в область на координатной плоскости и формирующий HTML-страницу с результатами проверки. Должен обрабатывать все запросы, содержащие сведения о координатах точки и радиусе области.
+ **Страница JSP**, формирующая HTML-страницу с веб-формой. Должна обрабатывать все запросы, не содержащие сведений о координатах точки и радиусе области.

### Разработанная страница JSP должна содержать:
1. "Шапку", содержащую ФИО студента, номер группы и номер варианта.
2. Форму, отправляющую данные на сервер.
3. Набор полей для задания координат точки и радиуса области в соответствии с вариантом задания.
4. Сценарий на языке JavaScript, осуществляющий валидацию значений, вводимых пользователем в поля формы.
5. Интерактивный элемент, содержащий изображение области на координатной плоскости (в соответствии с вариантом задания) и реализующий следующую функциональность:
      - Если радиус области установлен, клик курсором мыши по изображению должен обрабатываться JavaScript-функцией, определяющей координаты точки, по которой кликнул пользователь и отправляющей полученные координаты на сервер для проверки факта попадания.
      - В противном случае, после клика по картинке должно выводиться сообщение о невозможности определения координат точки.
      - После проверки факта попадания точки в область изображение должно быть обновлено с учётом результатов этой проверки (т.е., на нём должна появиться новая точка).
6. Таблицу с результатами предыдущих проверок. Список результатов должен браться из контекста приложения, HTTP-сессии или Bean-компонента в зависимости от варианта.

### Страница, возвращаемая AreaCheckServlet, должна содержать:
1. Таблицу, содержащую полученные параметры.
2. Результат вычислений --- факт попадания или непопадания точки в область.
3. Ссылку на страницу с веб-формой для формирования нового запроса.

Разработанное веб-приложение необходимо развернуть на сервере [WildFly](https://www.wildfly.org/). Сервер должен быть запущен в standalone-конфигурации, порты должны быть настроены в соответствии с выданным portbase, доступ к http listener'у должен быть открыт для всех IP.

#### Гайд по WildFly
1. скачать zip-архив с WildFly c официального сайта (обязательно Final, с Beta будут проблемы;
2. распаковать архив (на Линуксе с помощью утилиты `unzip`) и полученную директорию поместить на гелиос (\*nix: `scp`, windows (PuTTY): `pscp`);
      - на гелиос заходить, пробрасывая порты на тот порт, на котором будете смотреть лабораторную (*portbase*);
      В PuTTY под windows можно сделать во вкладке слева `Connection/SSH/Tunnels` и пробросить несколько портов сразу:
      
      ![PuTTY Guide](https://github.com/Come1LLF00/mvc__intro/blob/master/putty_guide.png "PuTTY")
      
3. в этой директории найти файл standalone.xml по пути standalone/configuration/standalone.xml

В этом файле заменить

        <interface name="public">
          <inet-address value="${jboss.bind.address:127.0.0.1}" />
        </interface>
на

        <interface name="public">
          <any-address />
        </interface>
а также строчку

        <socket-binding name="http" port="${jboss.http.port:8080}" />
        <socket-binding name="https" port="${jboss.https.port:443}"/>
на

        <socket-binding name="http" port="${jboss.http.port:<portbase>}"/>
где portbase --- взятое из гугл-таблицы число. Также желательно заменить в строках

        <socket-binding name="management-http" interface="management" port="${jboss.management.http.port:<initial_port>}"/>
        <socket-binding name="management-https" interface="management" port="${jboss.management.https.port:<initial_port>}"/>
значение  *<initial_port>* на свои, причем различные, благо под пользование выдается под 100 портов (должно хватить XD). Данная операция при прослушивании соответствующих портов откроет доступ к ГПИ (Графический Пользовательский Интерфейс) Wildfly. Вдобавок, можно установить свои порты и для этих параметров, но для пробрасывания пригодятся только http/https и management-http/s.

        <socket-binding name="ajp" port="${jboss.ajp.port:[initial_port]}"/>
        <socket-binding name="txn-recovery-environment" port="[initial_port]"/>
        <socket-binding name="txn-status-manager" port="[initial_port]"/>
При попытке обратиться к ГПИ Wildfly у вас в 99.99% случаев возникнет ошибка, связанная с отсутствием какого-либо пользователя, поэтому заранее можно запустить скрипт `bash <wildfly-path>/bin/add-user.sh` и уже заполнив имя и пароль, пропустив добавления в различные группы и введение дополнительных функций, можно будет полноценно воспользоваться ГПИ Wildfly, перейдя по адресу `localhost:<http/s-port>/` (перед этим не забыв пробросить нужные порты и запустив сервер, конечно же).

##### Стартовая страница Wildfly

![Wildfly Greetings](https://github.com/Come1LLF00/mvc__intro/blob/master/wildfly_index.png "WF Greet")

4. Деплоинг
      1. **Используя ГПИ**:
        + запускаете сервер: `bash <wildfly-path>/bin/standalone.sh` (желательно перед этим установить значение переменное окружения `JAVA` в `java18`.
        + заходите по адресу вида (не забыв перед этим пробросить порты): localhost:<management-http\s-port>/. Таким образом, вы должны попасть на страничку *HAL Management Console* (перед этим войдя под данными своего пользователя).
        + перейти на пункт **Deployments**.
        + с помощью стандартного интерфейса загрузки файла "задеплоить" Ваш WAR-архив (если это не архив, то сожмите все содержимое директории <name>.war с помощью ZIP-архиватора (\*nix: `zip <arhive-name>.war` <name>.war/*`).
      2. **Используя консоль**:
            - копируете war-архив с вашей лабораторной в директорию `<wildfly-path>/standalone/deployments`.
      
##### Страница управления сервером Wildfly

![Wildfly HMC](https://github.com/Come1LLF00/mvc__intro/blob/master/wildfly_management.png "WF Management Console")
      
#### N.B. для перезалива лабораторной знающие люди совеют использовать не Redeploy (как можно было подумать), а комбинацию Undeploy+Deploy.
5. если пробрасывали порты, то лабораторная доступна по адресу `localhost:<portbase>/<context-root>` (данный параметр указан/указывается в файле `WEB-INF/jboss-web.xml`).

### Вопросы к защите лабораторной работы:
1. Java-сервлеты. Особенности реализации, ключевые методы, преимущества и недостатки относительно CGI и FastCGI.
2. Контейнеры сервлетов. Жизненный цикл сервлета.
3. Диспетчеризация запросов в сервлетах. Фильтры сервлетов.
4. HTTP-сессии --- назначение, взаимодействие сервлетов с сессией, способы передачи идентификатора сессии.
5. Контекст сервелта --- назначение, способы взаимодействия сервлетов с контекстом.
6. JavaServer Pages. Особенности, преимуещества и недостатки по сравнению с сервлетами, область применения.
7. Жизненный цикл JSP.
8. Структура JSP-страницы. Комментарии, директивы, объявления, скриптлеты и выражения.
9. Правила записи Java-кода внутри JSP. Стандартные переменные, доступные в скриптлетах и выражениях.
10. Bean-компоненты и их использование в JSP.
11. Стандартные теги JSP. Использование Expression Language (EL) в JSP.
12. Параметры конфигурации JSP в дескрипторе развёртывания веб-приложения.
13. Шаблоны проектирования и архитектурные шаблоны. Использование в веб-приложениях.
14. Архитектура веб-приложений. Шаблон MVC. Архитектурные модели Model 1 и Model 2 и их реализация на платформе Java EE.
