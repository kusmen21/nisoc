<%@ page import="java.util.LinkedHashMap" %>
<%@ page import="java.util.List" %>
<%@ page import="sql.SQLHelper" %>
<%@ page import="java.util.Map" %>

<%
    request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");
    String[][] users = SQLHelper.getResultAsArray("SELECT * FROM users LEFT JOIN info ON id = info_id");
%>

<div class="main">
    <div class="info">
        <h1>Пользователи</h1>
        <%
            for (int i = 0; i < users.length; i++)
            {
                out.print("<p>");
                for (int j = 0; j < users[0].length; j++)
                {
                    out.print(users[i][j]);
                    out.print(" ");
                }
                out.print("</p>");
            }
        %>

        <p></p>
    </div>
</div>
