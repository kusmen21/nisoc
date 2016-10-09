<%@ page import="java.util.Date" %>

<div class="main">
    <div class="info">
        <h1>Профиль</h1>
        <p> ID: <%=request.getSession().getId()%></p>

        <h1>Сессия активна!</h1>
        <p> ID вашей сессии: <%=request.getSession().getId()%></p>
        <p> Дата создания: <%=new Date(request.getSession().getCreationTime()).toString()%></p>
        <p> Максимальное время инактивности: <%=request.getSession().getMaxInactiveInterval()%> секунд</p>
    </div>
</div>
