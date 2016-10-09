<%@ page import="sql.SQLHelper" %>
<%@ page import="exception.UniqueValueException" %>
<%@ page import="exception.NullValueException" %>
<%@ page import="exception.UnexpectedException" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");
%>

<!-- code to INFO page -->
<%! String refreshCode = ""; %>

<%
    String option = request.getParameter("option");
    String fname = request.getParameter("fname");
    String email = request.getParameter("email");
    String password = request.getParameter("password");

    StringBuilder sb = new StringBuilder();

    //from registration page
    if (option.equals("registration"))
    {
        try
        {
            SQLHelper.addUser(fname, email, password);
            sb.append("option=registration_success&message=Вы успешно зарегистрировались!");
        }
        catch (UniqueValueException e1)
        {
            sb.append("option=error&message=Поле Email должно быть уникальным");
            //out.println("<h1>Поле Email должно быть уникальным</h1>");
            sb.append("&sqlinfo2=email: " + email + " password: " + password);
        }
        catch (NullValueException e2)
        {
            sb.append("option=error&message=Все поля дожны быть заполнены");
            //out.println("<h1>Все поля дожны быть заполнены</h1>");
        }
        catch (UnexpectedException e3)
        {
            sb.append("option=error&message=Неизвестная ошибка");
            //out.println("<h1>Неизвестная ошибка</h1>");
        }
    }

    //from login page
    if (option.equals("login"))
    {
        if (SQLHelper.isSomethingFound("SELECT id FROM users WHERE email = '" + email +
                "' AND password = '" + password + "'"))
        {
            sb.append("option=login_success&message=Вы успешно вошли!");
            //session creating
            request.getSession().setAttribute("email", email);
            request.getSession().setAttribute("isOn", "true");
            request.getSession().setMaxInactiveInterval(30);
        }
        else
        {
            sb.append("option=error&message=Пользователь с такими email или паролем не найдены");
            sb.append("&sqlinfo2=email: ").append(email).append(" password: ").append(password);
        }
    }

    //shows user which founded
    sb.append("&sqlinfo=" + SQLHelper.getResultAsString("SELECT * FROM users WHERE email = '" + email + "'"));

    refreshCode = sb.toString();
%>

<!-- reference to INFO -->
<html>
<head>
    <meta content="text/html" charset="utf-8" />
    <title>NISOC</title>
    <meta http-equiv="refresh" content="0;URL=/nisocArtifact/info?<% out.println(refreshCode); %>"/>
</head>
<body>
</body>
</html>
