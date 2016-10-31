<%@ page import="java.util.Date" %>
<%@ page import="beans.User" %>
<%@ page import="beans.UserType" %>

<%
    request.setCharacterEncoding("UTF-8");

    int id;
    //user who came
    User thisUser = null;
    //user - elder of this page
    User elderUser;
    boolean isOwnPage = false;
    //check for empty user (for tests)
    boolean isEmpty = false;

    // if has no parameters
    try
    {
        id = Integer.parseInt(request.getParameter("id"));
    }
    catch (Exception e)
    {
        thisUser = (User) request.getSession().getAttribute("user");
        id = thisUser.getId();
    }

    try
    {
        if (thisUser == null){
            thisUser = (User) request.getSession().getAttribute("user");}
    }
    catch (NullPointerException e){}

    //is this page property of thisUser
    if (id == thisUser.getId())
    {
        isOwnPage = true;
        elderUser = thisUser;
    }
    else
    {
        elderUser = new User(id);
        if (elderUser.isEmpty())
        {
            isEmpty = true;
        }
    }

    String email = elderUser.getEmail();
    String fname = elderUser.getFname();
    String lname = elderUser.getLname();
    String nick = elderUser.getNick();
    Date dateOfRegistration = elderUser.getDateOfRegistration();
    UserType type = elderUser.getType();
%>

<div class="main">
<% if (!isEmpty){%>
    <div class="info">
        <h1>ПРОФИЛЬ</h1>

        <!-- is your page -->
        <%if (isOwnPage) out.println("<p>Твоя страница</p>");%>

        <!-- fname (nick) lname -->
        <p>
            <%=fname%>
            <%if (nick != null && nick.length() > 0) out.println("(" + nick + ")");%>
            <%if (lname != null && lname.length() > 0) out.println(lname);%>
        </p>

        <!-- email -->
        <%if (email != null && email.length() > 0) out.println("<p>EMAIL: " + email + "</p>");%>

        <!-- dateOfRegistration -->
        <%if (dateOfRegistration != null) out.println("<p>Дата регистрации: " + dateOfRegistration.toString() + "</p>");%>
    </div>
<% } else {%>
    <div class="info">
        <h1>Ошибка</h1>
        <p> Пользователь с ID = <%=id%> не найден</p>
    </div>
<% } %>
    <div class="info">
        <h1>ИНФО</h1>
        <p> ID вашей сессии: <%=request.getSession().getId()%></p>
        <p> Дата создания: <%=new Date(request.getSession().getCreationTime()).toString()%></p>
        <p> Максимальное время инактивности: <%=request.getSession().getMaxInactiveInterval()%> секунд</p>
    </div>
</div>
