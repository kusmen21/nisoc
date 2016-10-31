package beans;

import sql.SQLHelper;

import java.util.LinkedList;

public class Message
{
    private int messageId;
    private int senderId;
    private int receiverId;
    private String text;
    private String dateWhenSent;
    private String dateWhenRead;
    private boolean isRead;

    //first creating message
    public Message(int senderId, int receiverId, String text)
    {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.text = text;
        this.dateWhenSent = SQLHelper.getDateTimeAsString();
    }

    //full message initialize
    public Message(int messageId, int senderId, int receiverId, String text, String dateWhenSent, String dateWhenRead)
    {
        this.messageId = messageId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.text = text;
        this.dateWhenSent = dateWhenSent;

        if (dateWhenRead == null || dateWhenRead.length() == 0)
        {
            /*this.dateWhenRead = SQLHelper.getDateTimeAsString();
            this.isRead = true;
            saveChangesToDatabase();*/
        }
        else
        {
            this.dateWhenRead = dateWhenRead;
            isRead = true;
        }
    }

    public static LinkedList<Message> getMessages(int user, int user2)
    {
        LinkedList<Message> rezult = new LinkedList<Message>();
        String[][] table = null;

        //all messages, or chat with 2 persons
        if (user2 ==0)
        {
            table = SQLHelper.getMessages(user);
        }
        else
        {
            table = SQLHelper.getMessages(user, user2);
        }

        for (int i = 1; i < table.length; i++)
        {
            int messageId = Integer.parseInt(table[i][0]);
            int senderId = Integer.parseInt(table[i][1]);
            int receiverId = Integer.parseInt(table[i][2]);
            String text = table[i][3];
            String dateWhenSent = table[i][4];
            String dateWhenRead = table[i][5];

            Message message = new Message(messageId, senderId, receiverId, text, dateWhenSent, dateWhenRead);
            rezult.addLast(message);
        }
        return rezult;
    }



    /**
     * writing message to database and get messageId
     */
    public void sendMessage()
    {
        SQLHelper.sendMessage(senderId, receiverId, text);
        //messageId = SQLHelper.getMessageId(senderId);
    }

    /**
     * when you get text - you read this message
     * also saving read status to database
     */
    public String getText()
    {
        return text;
    }

    public int getSenderId() {
        return senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public boolean isRead() {
        return isRead;
    }

    public String getDateWhenSent() {
        return dateWhenSent;
    }

    public int getMessageId() {
        if (messageId != 0)
            return messageId;
        else return 0;
    }

    public String getDateWhenRead()
    {
        if (dateWhenRead != null)
            return dateWhenRead;
        else return null;
    }

    public void setRead()
    {
        isRead = true;
        dateWhenRead = SQLHelper.getDateTimeAsString();
        SQLHelper.saveMessageChangesToDatabase(messageId, dateWhenRead);
    }
}
