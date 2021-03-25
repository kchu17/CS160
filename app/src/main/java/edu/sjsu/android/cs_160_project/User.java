package edu.sjsu.android.cs_160_project;

public class User {
    private String fullName;
    private String email;

    public User(String name, String email)
    {
        this.fullName = name;
        this.email = email;

    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
