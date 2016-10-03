<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    request.setCharacterEncoding("UTF-8");

    String sqlinfo = "";
    String sqlinfo2 = "";
    String option = "";
    String message = "";
    String message2 = "";

    try {
        option = request.getParameter("option");
        message = request.getParameter("message");
        sqlinfo = request.getParameter("sqlinfo");
        sqlinfo2 = request.getParameter("sqlinfo2");
    }catch (Exception ignored){}
%>

<div class="main">
    <div class="info">
        <%
            if (option.equals("error")) out.println("<h1>Ошибка</h1>");
            if (option.equals("login_success"))
            {
                out.println("<h1>Вход</h1>");
                message2 = "Создана ваша сессия длительностью в 30 секунд. Во время жизни сессии вы не можете попасть " +
                        "на страницы регистации и логина. После 30 сек. инактивности " +
                        "вам нужно будет заного войти";
            }
            if (option.equals("registration_success")) out.println("<h1>Регистрация</h1>");
            if (option.equals("page_missed"))
            {
                out.println("<h1>Страница не существует</h1>");
                message = "Такой страницы нет, но она обязательно появится =)";
            }
        %>
        <p><% if (message != null) out.println(message); %></p>
        <p><% if (message2 != null) out.println(message2); %></p>
        <p><% if (sqlinfo != null) out.println(sqlinfo); %></p>
        <p><% if (sqlinfo2 != null) out.println(sqlinfo2); %></p>
    </div>
</div>