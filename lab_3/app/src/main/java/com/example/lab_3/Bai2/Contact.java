package com.example.lab_3.Bai2;

import org.intellij.lang.annotations.Identifier;

import javax.annotation.processing.Generated;

public class Contact {
    private int id;
    private String name;
    private String phoneNumber;

    // Getters and setters for the fields
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    // Default constructor
    public Contact() {
    }

    // Constructor with two parameters for name and phone number
    public Contact(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    // Constructor with all parameters
    public Contact(int id, String name, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }
}
