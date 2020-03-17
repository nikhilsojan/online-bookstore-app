package com.example.fireapp;

public class Book {
    String id;
    String name;
    String author;
    String genre;
    int count;
    String price;
    public Book() {
    }

    public Book(String id, String name, String author, String genre,int count,String price) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.count=count;
        this.price=price;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public int getCount() {
        return count;
    }

    public String getPrice() {
        return price;
    }
}
