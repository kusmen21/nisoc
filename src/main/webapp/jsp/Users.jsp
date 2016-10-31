<%@ page import="beans.User" %>
<%@ page import="java.util.LinkedList" %>

<%
    request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");
    String contextPath = request.getContextPath();
%>

<div class="main">
    <div class="user_list">
        <%
            LinkedList<User> users = User.getUsers();
            for (User user : users)
            {
                out.println(
                        "<div class=\"user\">\n" +
                        "   <img src=\"images/question.png\" height=\"75\" width=\"75\">   \n" +
                        "   <div class=\"user_info_area\">\n" +
                        "         <div class=\"user_name\">" + user.getFname() + "</div>\n" +
                        "         <div class=\"user_id\">ID: " + user.getId() + "</div>\n" +
                        "         <div class=\"user_dateOfRegistration\">Регистрация: " + user.getDateOfRegistration().toLocaleString() + "</div>\n" +
                        "   </div>\n" +
                        "   <div class=\"user_buttons\">\n" +
                        "         <a class=\"user_button\" href=\"" + user.getLink() + "\">СТРАНИЦА</a>\n" +
                        "         <a class=\"user_button\" href=\"" + contextPath + "/messages?receiverId=" + user.getId() + "\">ПЕРЕПИСКА</a>\n" + //////////////////////////////// CHANGE
                        "   </div>\n" +
                        "</div>");
            }
        %>
    </div>
</div>
