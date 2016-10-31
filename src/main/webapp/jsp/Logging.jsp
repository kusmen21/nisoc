<%@ page import="sql.SQLHelper" %>
<%@ page import="exception.UniqueValueException" %>
<%@ page import="exception.NullValueException" %>
<%@ page import="exception.UnexpectedException" %>
<%@ page import="beans.User" %>
<%@ page import="java.util.LinkedList" %>
<%@ page import="beans.Message" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");
%>

<%
    String option = request.getParameter("option");
    String fname = request.getParameter("fname");
    String email = request.getParameter("email");
    String password = request.getParameter("password");

    //from registration page
    if (option.equals("registration"))
    {
        try
        {
            SQLHelper.addUser(fname, email, password);
            request.getSession().setAttribute("option", "registration_success");
            request.getSession().setAttribute("message", "Вы успешно зарегистрировались!");
        }
        catch (UniqueValueException e1)
        {
            request.getSession().setAttribute("option", "error");
            request.getSession().setAttribute("message", "Поле Email должно быть уникальным!");
        }
        catch (NullValueException e2)
        {
            request.getSession().setAttribute("option", "error");
            request.getSession().setAttribute("message", "Все поля дожны быть заполнены!");
        }
        catch (UnexpectedException e3)
        {
            request.getSession().setAttribute("option", "error");
            request.getSession().setAttribute("message", "Неизвестная ошибка");
        }
    }

    //from login page
    if (option.equals("login"))
    {
        if (SQLHelper.isSomethingFound("SELECT id FROM users WHERE email = '" + email +
                "' AND password = '" + password + "'"))
        {
            //session creating
            request.getSession().setAttribute("option", "login_success");
            request.getSession().setAttribute("message", "Вы успешно вошли!");
            request.getSession().setAttribute("email", email);
            request.getSession().setAttribute("isOn", "true");
            request.getSession().setMaxInactiveInterval(60*10);

            //user copy to session
            request.getSession().setAttribute("user", new User(email));

            //creating messageList
            LinkedList<Message> messages = new LinkedList<Message>();
            request.getSession().setAttribute("messages", messages);
        }
        else
        {
            request.getSession().setAttribute("option", "error");
            request.getSession().setAttribute("message", "Пользователь с такими email или паролем не найдены");
            //request.getSession().setAttribute("sqlinfo2", "email: " + email + ", password: " + password);
        }
    }
    response.sendRedirect(request.getContextPath() + "/info");
    //for tests
    //shows user which founded
    //sb.append("&sqlinfo=" + SQLHelper.getResultAsString("SELECT * FROM users WHERE email = '" + email + "'"));
    //request.getSession().setAttribute("sqlinfo", SQLHelper.getResultAsString("SELECT * FROM users WHERE email = '" + email + "'"));
%>

<!-- reference to INFO
<html>
<head>
    <meta content="text/html" charset="utf-8" />
    <title>NISOC</title>
    <meta http-equiv="refresh" content="0;URL=/info"/>
</head>
<body>
</body>
</html>
-->
