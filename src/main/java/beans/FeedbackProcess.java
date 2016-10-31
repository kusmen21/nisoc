package beans;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "FeedbackProcess")
public class FeedbackProcess extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        int idSender = ((User) (request.getSession().getAttribute("user"))).getId();
        int idReceiver = Integer.parseInt(request.getParameter("idReceiver"));
        String text = request.getParameter("text");

        //sending message
        Message message = new Message(idSender, idReceiver, text);
        message.sendMessage();

        //info for user
        PrintWriter out = response.getWriter();
        out.println("Сообщение " + text + " отправлено!");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
