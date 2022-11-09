package com.example.contactmanager;

public class Contact {

    public String fname;
    public String lname;
    public String phone_number;
    public String password;

    public Contact(String fname, String lname, String phone_number, String pwd) {
        this.fname = fname;
        this.lname = lname;
        this.phone_number = phone_number;
        this.password = pwd;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public void setPassword(String pwd) {
        this.password = pwd;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", phone_number=" + phone_number +
                '}';
    }
}
