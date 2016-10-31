<%@ page import="java.util.LinkedList" %>
<%@ page import="beans.Message" %>
<%@ page import="beans.User" %>
<%@ page import="java.util.Iterator" %>
<%
    request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");

    //is it chatting to concrete user
    int receiverId = 0;
    try
    {
        receiverId = Integer.parseInt(request.getParameter("receiverId"));
    }
    catch (Exception e){e.printStackTrace();}
%>

<div class="main">

    <form class="message_send" action="<%=request.getContextPath()%>/processmessages" method="post">
        <div class="message_title">НОВОЕ СООБЩЕНИЕ</div>
        <input name="idReceiver" type="text" class="message_id" placeholder="ID" value="<%=receiverId != 0 ? receiverId : ""%>"/>
        <textarea name="text" class="message_input" required placeholder="Сообщение"></textarea>
        <input type="submit" name="submit" value="ОТПРАВИТЬ" class="message_button"/>
    </form>

    <div class="message_list">
        <div class="message_description">
            <%=receiverId != 0 ? "Открыта переписка с пользователем " + receiverId + ":" : "Все сообщения:"%>
        </div>
        <%
            try {
                int userId = ((User) (request.getSession().getAttribute("user"))).getId();
                LinkedList<Message> messages = null;

                //all messages, or chat with 2 persons
                if (receiverId == 0)
                {
                    messages = Message.getMessages(userId, 0);
                }
                else
                {
                    messages = Message.getMessages(userId, receiverId);
                }

                Iterator<Message> descendingIterator = messages.descendingIterator();
                while (descendingIterator.hasNext()) {
                    //get message from the end of list
                    Message message = descendingIterator.next();

                    //check is foreign message
                    String messageType = "message";
                    if (userId != message.getSenderId()) {
                        messageType = "message_foreign";
                    }

                    //set READ if not
                    if (userId == message.getReceiverId() && !message.isRead()) {
                        message.setRead();
                    }

                    //check isRead and set status
                    String messageIsRead = "";
                    String messageStatusForUser = "Нет";
                    if (!message.isRead())
                    {
                        messageIsRead = " message_unread";
                    }
                    else
                    {
                        messageStatusForUser = message.getDateWhenRead();
                    }

                    String toPrint =
                                    "<div class=\"" + messageType + messageIsRead + "\">" +
                                    "<div class=\"message_sender\">Кто: <a href=\"/main?id=" + message.getSenderId() + "\">" + message.getSenderId() + "</a></div>" +
                                    "<div class=\"message_receiver\">Кому: <a href=\"/main?id=" + message.getReceiverId() + "\">" + message.getReceiverId() + "</a></div>" +
                                    "<div class=\"message_text\">" + message.getText() + "</div>" +
                                    "<div class=\"message_date_sended\">Отправлено: " + message.getDateWhenSent() + "</div>" +
                                    "<div class=\"message_date_readed\">Прочитано: " + messageStatusForUser + "</div>" +
                                    "</div>";
                    out.println(toPrint);
                }
            }
            catch (Exception e)
            {
                //exception trowed when message count = 0
                e.printStackTrace();
            }
        %>
    </div>

</div>