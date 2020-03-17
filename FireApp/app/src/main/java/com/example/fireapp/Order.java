package com.example.fireapp;

public class Order {
    String id;
    String name;
    String email;
    String phone;
    String address;
    String date;
    Book book;

    public Order() {
    }

    public Order(String id, String name, String email, String phone, String address, String date, Book book) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.date = date;
        this.book=book;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getDate() {
        return date;
    }

    public Book getBook() {
        return book;
    }
}
