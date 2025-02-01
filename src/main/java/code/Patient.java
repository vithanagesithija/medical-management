package code;


public class Patient {
    private String id;
    private String name;
    private int age;
    private String phone;
    private String email;
    private String address;

    // Constructor
    public Patient(String id, String name, int age, String phone, String email, String address) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    // Getter methods
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }
}
