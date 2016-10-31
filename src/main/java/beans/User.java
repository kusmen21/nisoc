package beans;

import sql.SQLHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

public class User
{
    private int id;
    private String email;
    private String fname;
    private String lname;
    private String nick;
    private Date dateOfRegistration;
    private UserType type;
    private String link;

    //full initialize
    public User(int id, String email, String fname, String lname, String nick, Date dateOfRegistration, UserType type, String link)
    {
        this.id = id;
        this.email = email;
        this.fname = fname;
        this.lname = lname;
        this.nick = nick;
        this.dateOfRegistration = dateOfRegistration;
        this.type = type;
        this.link = link;
    }

    public User(int id)
    {
        try {
            String[][] table = SQLHelper.getResultAsArray("SELECT id, email, fname, lname, nick, dateOfRegistration," +
                    " type FROM users FULL JOIN info WHERE id = info_id and id = " + id);

            this.id = Integer.parseInt(table[1][0]);
            this.email = table[1][1];
            this.fname = table[1][2];
            this.lname = table[1][3];
            this.nick = table[1][4];

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            this.dateOfRegistration = simpleDateFormat.parse(table[1][5]);
            //this.dateOfRegistration = (table[1][5]);

            if (table[1][6].equalsIgnoreCase("ADMIN"))
            {
                this.type = UserType.ADMIN;
            }else
            if (table[1][6].equalsIgnoreCase("USER"))
            {
                this.type = UserType.USER;
            }
            // CHANGE IT
            link = "/nisocArtifact/main?id=" + id;                      // CHANGE IT
            // CHANGE IT
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public User(String email)
    {
        try {
            String[][] table = SQLHelper.getResultAsArray("SELECT id, email, fname, lname, nick, dateOfRegistration," +
                    " type FROM users FULL JOIN info WHERE id = info_id and email = '" + email + "'");

            this.id = Integer.parseInt(table[1][0]);
            this.email = table[1][1];
            this.fname = table[1][2];
            this.lname = table[1][3];
            this.nick = table[1][4];

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            this.dateOfRegistration = simpleDateFormat.parse(table[1][5]);
            //this.dateOfRegistration = (table[1][5]);

            if (table[1][6].equalsIgnoreCase("ADMIN"))
            {
                this.type = UserType.ADMIN;
            }else
            if (table[1][6].equalsIgnoreCase("USER"))
            {
                this.type = UserType.USER;
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public boolean isEmpty()
    {
        return id == 0;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", nick='" + nick + '\'' +
                ", dateOfRegistration=" + dateOfRegistration +
                ", type=" + type +
                '}';
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getNick() {
        return nick;
    }

    public Date getDateOfRegistration() {
        return dateOfRegistration;
    }

    public UserType getType() {
        return type;
    }

    public String getLink() {
        return link;
    }

    public static LinkedList<User> getUsers()
    {
        LinkedList<User> rezult = new LinkedList<User>();
        String[][] table = SQLHelper.getUsers();

        try {
            for (int i = 1; i < table.length; i++)
            {
                int id = Integer.parseInt(table[i][0]);
                String email = table[i][1];
                String fname = table[i][2];
                String lname = table[i][3];
                String nick = table[i][4];

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date dateOfRegistration = simpleDateFormat.parse(table[i][5]);

                UserType type = null;
                if (table[i][6].equalsIgnoreCase("ADMIN")) {
                    type = UserType.ADMIN;
                } else if (table[i][6].equalsIgnoreCase("USER")) {
                    type = UserType.USER;
                }
                String link = "/main?id=" + id;
                rezult.add(new User(id, email, fname, lname, nick, dateOfRegistration, type, link));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return rezult;
    }
}
