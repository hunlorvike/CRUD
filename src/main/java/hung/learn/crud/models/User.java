package hung.learn.crud.models;

public class User {
    private int Id;
    private String Username;
    private  String Password;
    private String Name;

    public User(){}

    public User(String username, String password, String name) {
        Username = username;
        Password = password;
        Name = name;
    }

    public User(int id, String username, String password, String name) {
        Id = id;
        Username = username;
        Password = password;
        Name = name;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "Id=" + Id +
                ", Username='" + Username + '\'' +
                ", Password='" + Password + '\'' +
                ", Name='" + Name + '\'' +
                '}';
    }
}
