package com.class113.finalsh;

public class User {
    private String userName;
    private String password;
    private String email ;

    public User(String fullName, String password, String email) {
        this.userName = fullName;
        this.password = password;
        this.email = email;
    }
    public User(){//to enable firebase to do its work
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String fname) {
        this.userName = fname;
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
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
