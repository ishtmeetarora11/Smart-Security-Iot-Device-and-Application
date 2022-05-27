package com.example.smartsecurityminor;

public class Users {
    private String username;
    private String email;
    private String id;

    public Users(String username, String email, String userId) {
        this.username = username;
        this.email = email;
        this.id = id;
    }

    public Users(){

    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
