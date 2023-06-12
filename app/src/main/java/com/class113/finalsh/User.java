package com.class113.finalsh;

public class User {
    private String  fullName , password,email ;

    public User(String fullName, String password, String email) {
        this.fullName = fullName;
        this.password = password;
        this.email = email;
    }
    public User(){//to enable firebase to do its work
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fname) {
        this.fullName = fname;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "fullName='" + fullName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
