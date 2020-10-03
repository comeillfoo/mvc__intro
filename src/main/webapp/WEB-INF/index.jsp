<%-- 
    Document   : index
    Created on : 3 oct. 2020 г., 1:10:56
    Author     : comeillfoo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="RU-ru">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Попадание точек | P3214</title>
  </head>
  <body>
    <table>
      <caption id="logo"></caption>
      <thead>
        <tr><th>имя</th><td>Ленар</td></tr>
        <tr><th>фамилия</th><td>Ханнанов</td></tr>
        <tr><th>отчество</th><td>Ильнурович</td></tr>
        <tr><th>группа</th><td>P3214</td></tr>
        <tr><th>вариант</th><td>2531</td></tr>
        <tr><th>рабочая область</th><th>окно вывода</th></tr>
      </thead>

      <tbody>
        <tr>
          <td><canvas width="240" height="240"></canvas></td>
          <td></td>
        </tr>

        <%-- --%>
        <tr>
          <td>
            <form method="GET" action="<%= request.getContextPath() %>" onsumbit="return validate(this);">
              <fieldset title="Выбрать из выпадающего списка значение x.">
                <label>изменение x
                  <select name="x">
                    <% for (int i = -5; i < 4; ++i) { %>
                    <option value="<%= i %>"><%= i %></option>
                    <% } %>
                  </select>
                </label>
              </fieldset>

              <fieldset title="Параметр y должен быть действительным числом и обязательно лежать в интервале (-3; 5).">
                <label>изменение y
                  <input type="text" name="y" placeholder="y&isin;(-3; 5)" required>
                </label>
              </fieldset>

              <fieldset title="Кнопка-переключатель: циклично изменяет значение при каждом нажатии с 1 по 5.">
                <label>изменение r
                  <input type="button" value="1" onclick="document.getElementById('r').value = this.value;">
                </label>
                <!-- hidden element to cast radius-parameter in query__string -->
                <input id="r" type="hidden" name="r">
              </fieldset>
                  
              <fieldset title="Кнопка отправки формы на проверку.">
                <input type="submit" name="<%= request.getSession().getId() %>" value="check">
              </fieldset>
            </form>
          </td>
        </tr>
        
        <tr>
        </tr>
      </tbody>
    </table>
  </body>
</html>
