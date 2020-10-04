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
    <title>Попадание точек | P3214</title>
    <link rel="stylesheet" type="text/css" href="assets/stylesheets/main.css">
    <link rel="stylesheet" type="text/css" href="assets/stylesheets/form.css">
  </head>
  <body>
    <table>
      <caption id="logo"></caption>
      <!-- header of the page-->
      <thead>
        <tr><th>имя</th><td>Ленар</td></tr>
        <tr><th>фамилия</th><td>Ханнанов</td></tr>
        <tr><th>отчество</th><td>Ильнурович</td></tr>
        <tr><th>группа</th><td>P3214</td></tr>
        <tr><th>вариант</th><td>2531</td></tr>
        <tr><th>рабочая область</th><th>окно вывода</th></tr>
      </thead>

      <!-- section of the page-->
      <tbody>
        <tr>
          <td><canvas width="240" height="240"></canvas></td>
          <td></td>
        </tr>

        <!-- client form for sending the data of point -->
        <tr>
          <td>
            <form method="GET" action="<%= request.getContextPath() %>" onsumbit="return validate(this);">
              <!-- X-changing field of selection -->
              <fieldset title="Выбрать из выпадающего списка значение x.">
                <label>изменение x
                  <select name="x">
                    <% for (int i = -5; i < 4; ++i) { %>
                    <option value="<%= i %>"><%= i %></option>
                    <% } %>
                  </select>
                </label>
              </fieldset>

              <!-- Y-changing field of selection -->
              <fieldset title="Параметр y должен быть действительным числом и обязательно лежать в интервале (-3; 5).">
                <label>изменение y
                  <input type="text" name="y" placeholder="y&isin;(-3; 5)" required>
                </label>
              </fieldset>

              <!-- R-changing field of selection -->
              <fieldset title="Кнопка-переключатель: циклично изменяет значение при каждом нажатии с 1 по 5.">
                <label>изменение r
                  <input type="button" value="1" onclick="document.getElementById('r').value = this.value;">
                </label>
                <!-- hidden element to cast radius-parameter in query__string -->
                <input id="r" type="hidden" name="r">
              </fieldset>
                  
              <!-- field of form submitting -->
              <fieldset title="Кнопка отправки формы на проверку.">
                <input type="submit" name="<%= request.getSession().getId() %>" value="<%= AreaCheckServlet.KEY_WORD %>">
              </fieldset>
            </form>
          </td>
        </tr>
        
        <tr>
          <td>
            <!-- table of previous tests -->
            <table>
              <caption id="results">предыдущие результаты</caption>
              <thead>
                <tr>
                  <th>дата проверки</th>
                  <th>время</th>
                  <th>x</th>
                  <th>y</th>
                  <th>r</th>
                  <th>результат</th>
                </tr>
              </thead>
              
              <tbody>
                <jsp:useBean id="results" scope="session" type="java.util.List" class="java.util.ArrayList" />
                <c:forEach var="result" items="${results}">
                <tr>
                  <td>${result.date}</td>
                  <td>${result.time}</td>
                  <td>${result.x}</td>
                  <td>${result.y}</td>
                  <td>${result.r}</td>
                  <td>${result.hit}</td>
                </tr>
                </c:forEach>
              </tbody>
            </table>
          </td>
        </tr>
      </tbody>
    </table>
  </body>
</html>
