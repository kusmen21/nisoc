package sql;

import exception.NullValueException;
import exception.UnexpectedException;
import exception.UniqueValueException;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.sql.*;
import java.util.*;


public class SQLHelper
{
    private static Statement statement = null;
    private static Connection connection = null;

    //for local instance of MYSQL
    //InitialContext ic = new InitialContext();
    //DataSource ds = (DataSource) ic.lookup("jdbc/Library");
    //Connection connection = ds.getConnection();

    private SQLHelper(){}

    private static Statement getStatement() throws SQLException
    {
        if (connection == null || connection.isClosed() || !connection.isValid(500))
        {
            connection = DriverManager.getConnection("jdbc:mysql://eu-cdbr-west-01.cleardb.com", "b9b900984b2170", "30274403");
        }

        statement = connection.createStatement();
        statement.execute("USE heroku_d93c849370b9e33");

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

    public static Map<String, List<String>> executeQuery(String command)
    {
        Map<String, List<String>> table = new HashMap<>();
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
        Map<String, List<String>> table = executeQuery(command);
        StringBuilder sb = new StringBuilder();

        for(Map.Entry<String, List<String>> pair : table.entrySet())
        {
            sb.append(pair.getKey()).append(" = ").append(pair.getValue().toString()).append("; ");
        }
        return sb.toString();
    }

    public static String[][] getResultAsArray(String command)
    {
        Map<String, List<String>> table = executeQuery(command);
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

                Calendar calendar = Calendar.getInstance();

                StringBuilder date = new StringBuilder();
                calendar.add(Calendar.MONTH, 1);
                date.append(calendar.get(Calendar.YEAR)).append("-").
                        append(calendar.get(Calendar.MONTH)).append("-").
                        append(calendar.get(Calendar.DAY_OF_MONTH));

                int id = Integer.parseInt(SQLHelper.executeQuery
                        ("SELECT (id) FROM users WHERE email = '" + email + "'").get("id").get(0));

                SQLHelper.execute("INSERT INTO info (info_id, dateOfRegistation) VALUES " +
                        "('" + id +  "', '" + date.toString() + "')");
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
 }
