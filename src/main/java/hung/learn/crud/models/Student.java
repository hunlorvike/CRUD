package hung.learn.crud.models;

public class Student {
    private int Id;
    private String Name;
    public String Email;
    public String Address;

    public Student() {
    }

    public Student(int id, String name, String email, String aAddress) {
        Id = id;
        Name = name;
        Email = email;
        Address = aAddress;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String aAddress) {
        Address = aAddress;
    }

    @Override
    public String toString() {
        return "Student{" +
                "Id=" + Id +
                ", Name='" + Name + '\'' +
                ", Email='" + Email + '\'' +
                ", Address='" + Address + '\'' +
                '}';
    }
}
