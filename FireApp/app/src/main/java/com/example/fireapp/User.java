package com.example.fireapp;

public class User {
    String id;
    String email;
    String password;
    String name;
    String number;

    public User(String id,String email, String password, String name, String number) {
        this.id=id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.number = number;
    }
    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }
}
