<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    request.setCharacterEncoding("UTF-8");

    String sqlinfo = "";
    String sqlinfo2 = "";
    String option = "";
    String message = "";
    String message2 = "";

    try {
        option = (String) request.getSession().getAttribute("option");
        message = (String) request.getSession().getAttribute("message");
        sqlinfo = (String) request.getSession().getAttribute("sqlinfo");
        sqlinfo2 = (String) request.getSession().getAttribute("sqlinfo2");
    }
    catch (Exception ignored){}
%>

<div class="main">
    <div class="info">
        <%
            if (option.equals("error")) out.println("<h1>ОШИБКА</h1>");
            else if (option.equals("login_success"))
            {
                out.println("<h1>ВХОД</h1>");
                message2 = "Создана ваша сессия длительностью в 10 минут. Теперь у вас есть доступ ко всему " +
                        "функционалу. Enjoy!";
            }
            else if (option.equals("registration_success"))
            {
                out.println("<h1>РЕГИСТРАЦИЯ</h1>");
            }
            else if (option.equals("page_missed"))
            {
                out.println("<h1>СТРАНИЦА НЕ СУЩЕСТВУЕТ</h1>");
                message = "Такой страницы нет, но она обязательно появится =)";
            }
            else if (option.equals("message_sent"))
            {
                out.println("<h1>СООБЩЕНИЕ ОТПРАВЛЕНО</h1>");
                message = "";
            }
            request.getSession().setAttribute("option", "");
        %>
        <p><% if (message != null) out.println(message); %></p>
        <p><% if (message2 != null) out.println(message2); %></p>
        <p><% if (sqlinfo != null) out.println(sqlinfo); %></p>
        <p><% if (sqlinfo2 != null) out.println(sqlinfo2); %></p>
    </div>
</div>