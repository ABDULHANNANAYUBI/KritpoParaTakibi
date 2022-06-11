package com.example.kpt.view;

public class Customer {

    public Customer(String id,String name, String surname, String email,String password, int isActive, int activeAfter) {
        Id = id;
        Name = name;
        Surname = surname;
        Email = email;
        Password = password;
        IsActive = isActive;
        ActiveAfter = activeAfter;
    }
    public Customer(){

    }
    public String getPassword() {return Password;}
    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSurname() {
        return Surname;
    }

    public void setSurname(String surname) {
        Surname = surname;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public int isActive() {
        return IsActive;
    }

    public void setActive(int active) {
        IsActive = active;
    }

    public int getActiveAfter() {
        return ActiveAfter;
    }

    public void setActiveAfter(int activeAfter) {
        ActiveAfter = activeAfter;
    }

    public void setPassword (String password) {Password = password;}
    private String Id;
    private String Name;
    private String Surname;
    private String Email;
    private String Password;
    private int IsActive;
    private int ActiveAfter;
}
