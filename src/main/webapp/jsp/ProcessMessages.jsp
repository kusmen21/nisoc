<%@ page import="sql.SQLHelper" %>
<%@ page import="beans.User" %>
<%@ page import="beans.Message" %>
<%@ page import="java.util.LinkedList" %>
<%
    request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");

    //creating message
    int idSender = ((User) (request.getSession().getAttribute("user"))).getId();
    int idReceiver = Integer.parseInt(request.getParameter("idReceiver"));
    String text = request.getParameter("text");
    Message message = new Message(idSender, idReceiver, text);

    //sending message
    message.sendMessage();

    response.sendRedirect(request.getContextPath() + "/messages?receiverId=" + idReceiver);

    //copy message to session cache
    //((LinkedList<Message>) (session.getAttribute("messages"))).addLast(message);

    //send info to user by infoPage
    //request.getSession().setAttribute("option", "message_sent");
    //response.sendRedirect(request.getContextPath() + "/info");
%>