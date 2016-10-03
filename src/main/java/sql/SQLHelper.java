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

            while (rs.next()) {
                for (int columnNumber = 1; columnNumber <= columnsCount; columnNumber++) {
                    String columnName = data.getColumnName(columnNumber);
                    if (!table.containsKey(columnName)) {
                        List<String> values = new ArrayList<>();
                        table.put(columnName, values);
                    }
                    table.get(columnName).add(rs.getString(columnNumber));
                }
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

    public static boolean isSomethingFound(String command)
    {
        Map<String, List<String>> table = executeQuery(command);
        return !table.isEmpty();
    }

    public synchronized static void addUser(String fname, String email, String password)
            throws NullValueException, UniqueValueException, UnexpectedException
    {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("D:/helper.txt");
            fileWriter.write(getResultAsString("SELECT (id) FROM users where email = '" + email + "'"));
            fileWriter.flush();
            fileWriter.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
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

                SQLHelper.execute("INSERT INTO users (type, email, password) VALUES " +
                            "('USER', '" + email + "', '" + password + "')");

                Calendar calendar = Calendar.getInstance();

                StringBuilder date = new StringBuilder();
                calendar.add(Calendar.MONTH, 1);
                date.append(calendar.get(Calendar.YEAR)).append("-").
                        append(calendar.get(Calendar.MONTH)).append("-").
                        append(calendar.get(Calendar.DAY_OF_MONTH));

                int id = Integer.parseInt(SQLHelper.executeQuery
                        ("SELECT (id) FROM users WHERE email = '" + email + "'").get("id").get(0));

                SQLHelper.execute("INSERT INTO info (info_id, fname, dateOfRegistation) VALUES " +
                        "('" + id +  "', '" + fname + "', '" + date.toString() + "')");
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
