package code;

public class Doctor {
    private String docID;
    private String name;
    private String specialization;
    private String phone;
    private String email;

    public Doctor(String docID, String name, String specialization, String phone, String email) {
        this.docID = docID;
        this.name = name;
        this.specialization = specialization;
        this.phone = phone;
        this.email = email;
    }

    public String getDocID() {
        return docID;
    }

    public String getName() {
        return name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

