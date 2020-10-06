<%-- 
    Document   : index
    Created on : 3 oct. 2020 г., 1:10:56
    Author     : comeillfoo
--%>

<%@page import="java.io.PrintWriter"%>
<%@page import="java.io.IOException"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="wp2531.mvc__intro.AreaCheckServlet"%>
<%@page import="wp2531.mvc__intro.ResultBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="RU-ru">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Точки на области | Форма</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/assets/stylesheets/main.css">
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/assets/stylesheets/form.css">
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/assets/stylesheets/results.css">
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  </head>
  <body>
    <table class="wrapper">
      <caption id="logo"><a class="title__main" href="<%= request.getContextPath() %>">Точки на области | Dots among the Area</a></caption>
      <!-- header of the page -->
      <thead>
        <tr><td id="avatar" rowspan="5"><img class="field__board--rounded" src="<%= request.getContextPath() %>/assets/images/avatar.png"></td><th class="cell__heading cell__text--capital title__align--right title__after--colon">имя</th><td class="title__text--field title__text--underlined">Ленар</td></tr>
        <tr><th class="cell__heading cell__text--capital title__align--right title__after--colon">фамилия</th><td class="title__text--field title__text--underlined">Ханнанов</td></tr>
        <tr><th class="cell__heading cell__text--capital title__align--right title__after--colon">отчество</th><td class="title__text--field title__text--underlined">Ильнурович</td></tr>
        <tr><th class="cell__heading cell__text--capital title__align--right title__after--colon">группа</th><td class="title__text--field title__text--underlined">P3214</td></tr>
        <tr><th class="cell__heading cell__text--capital title__align--right title__after--colon">вариант</th><td class="title__text--field title__text--underlined">2531</td></tr>
        <tr class="row__heading"><th colspan="3" class="cell__text--capital">рабочая область</th></tr>
      </thead>

      <!-- section of the page-->
      <tbody>
        <tr>
          <td colspan="3"><canvas id="area" class="field__board--rounded" width="600" height="600" ></canvas></td>
        </tr>

        <!-- client form for sending the data of point -->
        <tr>
          <td colspan="3">
            <form method="GET" action="<%= request.getContextPath() %>" onsubmit="return validateForm('js-y-changing', 'radius-changing');">
              <!-- X-changing field of selection -->
              <fieldset class="container__float container__float--p1-3 field__board--rounded" title="Выбрать из выпадающего списка значение x.">
                <label class="title__text--capital">изменение x<br>
                  <select name="x" class="input__field--centred">
                    <% for (int i = -5; i < 4; ++i) { %>
                    <option value="<%= i %>"><%= i %></option>
                    <% } %>
                  </select>
                </label>
              </fieldset>

              <!-- Y-changing field of selection -->
              <fieldset class="container__float container__float--p1-3 field__board--rounded" title="Параметр y должен быть действительным числом (десятичный разделитель точка <.>) и обязательно лежать в интервале (-3; 5).">
                <label class="title__field title__text--capital">изменение y<br>
                  <input id="js-y-changing" class="input__field--centred" type="text" name="y" placeholder="y&isin;(-3; 5)" required>
                </label>
              </fieldset>

              <!-- R-changing field of selection -->
              <fieldset class="container__float container__float--p1-3 field__board--rounded" title="Кнопка-переключатель: циклично изменяет значение при каждом нажатии с 1 по 5.">
                <label class="title__field title__text--capital">изменение r<br>
                  <input class="btn btn__size--level input__field--centred" type="button" value="1" title="выбрано: 1" onclick="handle_drawing(this.value, 5); toggle(this, 'radius-changing');">
                </label>
                <!-- hidden element to cast radius-parameter in query__string -->
                <input id="radius-changing" type="hidden" name="r">
              </fieldset>
                  
              <!-- field of form submitting -->
              <fieldset class="container" title="Кнопка отправки формы на проверку.">
                <input id="submit" class="btn btn__size--submit" type="submit" name="<%= request.getSession().getId() %>" value="<%= AreaCheckServlet.KEY_WORD %>">
              </fieldset>
            </form>
          </td>
        </tr>
        
        <tr>
          <td colspan="3">
            <!-- table of previous tests -->
            <table id="resulting-table" class="results__table--fill">
              <caption id="results" class="title__text--capital">предыдущие результаты</caption>
              <thead>
                <tr class="results__row--heading">
                  <th class="cell__text--capital">дата проверки</th>
                  <th>Время, мс</th>
                  <th class="cell__text--capital">x</th>
                  <th class="cell__text--capital">y</th>
                  <th class="cell__text--capital">r</th>
                  <th class="cell__text--capital">результат</th>
                </tr>
              </thead>
              
              <tbody>
                <jsp:useBean id="results" scope="session" type="java.util.List" class="java.util.ArrayList" />
                <c:forEach var="result" items="${results}">
                <tr class="results__row--data">
                  <td class="results__cell--offset"><div class="results__cell--scrollable">${result.date}</div></td>
                  <td class="results__cell--offset"><div class="results__cell--scrollable">${result.time}</div></td>
                  <td class="results__cell--offset"><div class="results__cell--scrollable">${result.x}</div></td>
                  <td class="results__cell--offset"><div class="results__cell--scrollable">${result.y}</div></td>
                  <td class="results__cell--offset"><div class="results__cell--scrollable">${result.r}</div></td>
                  <td class="results__cell--offset"><div class="results__cell--scrollable"><c:choose><c:when test="${result.hit}">да</c:when><c:otherwise>нет</c:otherwise></c:choose></div></td>
                </tr>
                </c:forEach>
              </tbody>
            </table>
          </td>
        </tr>
      </tbody>
    </table>
                
    <script type="text/javascript" src="<%= request.getContextPath() %>/assets/scripts/validation.js"></script>
    <script type="text/javascript" src="<%= request.getContextPath() %>/assets/scripts/mapping.js"></script>
  </body>
</html>
