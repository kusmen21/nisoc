package sql;

import beans.User;
import exception.NullValueException;
import exception.UnexpectedException;
import exception.UniqueValueException;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.sql.*;
import java.util.*;
import java.util.Date;


public class SQLHelper
{
    private static Statement statement = null;
    private static Connection connection = null;



    private SQLHelper(){}

    private static Statement getStatement() throws SQLException
    {
        if (connection == null || connection.isClosed() || !connection.isValid(500))
        {
            //for local instance of MYSQL
            //InitialContext ic = new InitialContext();
            //DataSource ds = (DataSource) ic.lookup("jdbc/Library");
            //Connection connection = ds.getConnection();

            //for clear db
            //connection = DriverManager.getConnection("jdbc:mysql://eu-cdbr-west-01.cleardb.com", "b9b900984b2170", "30274403");

            //for jawsdb
            connection = DriverManager.getConnection("jdbc:mysql://" +
                    "f8ogy1hm9ubgfv2s.chr7pe7iynqr.eu-west-1.rds.amazonaws.com",
                    "x52w8cv19q9ov2sf",
                    "t50jzbw6catn8u98");
        }

        statement = connection.createStatement();
        statement.execute("USE g3aqxxmtvx8mw5e1");

        //for the charset problems
        //statement.execute("SET NAMES utf8 COLLATE utf8_unicode_ci");
        //statement.execute("SET CHARACTER SET 'utf8'");
        //statement.execute("SET SESSION collation_connection = 'utf8_general_ci'");

        return statement;
    }

    private static void closeResources()
    {
        try
        {
            statement.close();
            //connection.close();
        }
        catch (Exception ignored){}
    }

    public synchronized static void execute(String command)
    {
        try
        {
            getStatement().execute(command);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            closeResources();
        }
    }

    public static LinkedHashMap<String, List<String>> executeQuery(String command)
    {
        LinkedHashMap<String, List<String>> table = new LinkedHashMap<>();
        ResultSet rs;

        try {
            synchronized (SQLHelper.class)
            {
                rs = getStatement().executeQuery(command);
            }
            ResultSetMetaData data = rs.getMetaData();
            int columnsCount = data.getColumnCount();

            boolean isFirstRow = true;
            while (rs.next()) //get row
            {
                for (int columnNumber = 1; columnNumber <= columnsCount; columnNumber++) //go to row columns
                {
                    String columnName = data.getColumnName(columnNumber);   //get column name
                    if (isFirstRow) {
                        List<String> values = new ArrayList<>();
                        table.put(columnName, values);
                    }
                    table.get(columnName).add(rs.getString(columnNumber));  //add value from row and this column
                }
                isFirstRow = false;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            closeResources();
        }

        return table;
    }

    public static String getResultAsString(String command)
    {
        LinkedHashMap<String, List<String>> table = executeQuery(command);
        StringBuilder sb = new StringBuilder();

        for(Map.Entry<String, List<String>> pair : table.entrySet())
        {
            sb.append(pair.getKey()).append(" = ").append(pair.getValue().toString()).append(" ");
        }
        return sb.toString();
    }

    public static String[][] getResultAsArray(String command)
    {
        LinkedHashMap<String, List<String>> table = executeQuery(command);
        String[][] rezult = null;

        int column = 0;
        for(Map.Entry<String, List<String>> pair : table.entrySet())
        {
            if (rezult == null) {
                rezult = new String[pair.getValue().size() + 1][table.size()];
            }
            rezult[0][column] = pair.getKey();  //updating column

            for (int i = 0; i < pair.getValue().size(); i++)
            {
                rezult[i+1][column] = pair.getValue().get(i);
            }
            column++;
        }

        return rezult;
    }

    public static boolean isSomethingFound(String command)
    {
        Map<String, List<String>> table = executeQuery(command);
        return !table.isEmpty();
    }

    public synchronized static void addUser(String fname, String email, String password)
            throws NullValueException, UniqueValueException, UnexpectedException
    {
        try
        {
            if (    fname != null & !fname.equals("") &
                    password != null & !password.equals("") &
                    email != null & !email.equals(""))
            {
                if (isSomethingFound("SELECT (id) FROM users where email = '" + email + "'"))
                {
                    throw new UniqueValueException();
                }

                SQLHelper.execute("INSERT INTO users (type, email, fname, password) VALUES " +
                            "('USER', '" + email + "', '" + fname + "', '" + password + "')");

                String date = getDateTimeAsString();

                int id = Integer.parseInt(SQLHelper.executeQuery
                        ("SELECT (id) FROM users WHERE email = '" + email + "'").get("id").get(0));

                SQLHelper.execute("INSERT INTO info (info_id, dateOfRegistration) VALUES " +
                        "('" + id +  "', '" + date + "')");
            }
            else
            {
                throw new exception.NullValueException();
            }
        }catch (RuntimeException e)
        {
            throw new exception.UnexpectedException();
        }
    }

    public static void sendMessage(int sender, int receiver, String text)
    {
        String dateWhenSent = getDateTimeAsString();

        SQLHelper.execute("INSERT INTO messages (sender, receiver, text, dateWhenSent) VALUES " +
                "(" + sender + ", " + receiver + ", '" + text + "', '" + dateWhenSent + "')");
    }

    public static int checkForMessages(User user)
    {
        return Integer.parseInt(SQLHelper.getResultAsArray
                ("SELECT COUNT(*) FROM messages WHERE receiver = " + user.getId() + " AND isRead = FALSE")[1][0]);
    }

    public static int getMessageId(int sender)
    {
        return Integer.parseInt(SQLHelper.getResultAsArray
                ("SELECT MAX(messageId) FROM messages WHERE sender = " + sender)[1][0]);
    }

    public static void saveMessageChangesToDatabase(int messageId, String dateTime)
    {
        SQLHelper.execute("UPDATE messages SET isRead = TRUE, dateWhenRead = '" + dateTime +
                "' WHERE messageId = " + messageId);
    }

    public static String[][] getMessages(int user)
    {
        return SQLHelper.getResultAsArray("SELECT messageId, sender, receiver, text, dateWhenSent, dateWhenRead " +
                "FROM messages WHERE sender = " + user + " OR receiver = " + user);
    }

    public static String[][] getMessages(int userFirst, int userSecond)
    {
        return SQLHelper.getResultAsArray("SELECT messageId, sender, receiver, text, dateWhenSent, dateWhenRead " +
                "FROM messages WHERE sender = " + userFirst + " AND receiver = " + userSecond + " OR sender = " +
                userSecond + " AND receiver = " + userFirst);
    }

    public static String[][] getUsers()
    {
        return SQLHelper.getResultAsArray("SELECT id, email, fname, lname, nick, dateOfRegistration," +
                " type FROM users FULL JOIN info WHERE id = info_id");
    }

    public static String getDateTimeAsString()
    {
        Calendar calendar = Calendar.getInstance();
        StringBuilder date = new StringBuilder();
        calendar.add(Calendar.MONTH, 1);
        date.append(calendar.get(Calendar.YEAR)).append("-").
                append(calendar.get(Calendar.MONTH)).append("-").
                append(calendar.get(Calendar.DAY_OF_MONTH)).append(" ").
                append(calendar.get(Calendar.HOUR_OF_DAY)).append(":").
                append(calendar.get(Calendar.MINUTE)).append(":").
                append(calendar.get(Calendar.SECOND));
        return date.toString();
    }
 }
