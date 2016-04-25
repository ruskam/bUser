package com.example.esr14.signupplay;

/**
 * Created by ESR14 on 28/03/16.
 */
public class User {

    String name, uname, email, password;

    public User() {

    }

    public User(String uname, String password) {
        this.uname = uname;
        this.password = password;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
