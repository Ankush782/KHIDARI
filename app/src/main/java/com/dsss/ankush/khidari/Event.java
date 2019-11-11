package com.dsss.ankush.khidari;

import java.io.Serializable;

public class Event implements Serializable{
    String name,sports,state,city,address,date,time,fee,istprize,istrunnerup,secondrunnerup,notes,author,profile_pic;



    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public Event(String name, String sports, String venue, String date, String time, String fee, String istprize, String istrunnerup, String secondrunnerup, String notes) {
        this.name = name;
        this.sports = sports;
       // this.venue = venue;
        this.date = date;
        this.time = time;

        this.fee = fee;
        this.istprize = istprize;
        this.istrunnerup = istrunnerup;
        this.secondrunnerup = secondrunnerup;
        this.notes = notes;
    }

    public Event(String name, String sports, String state, String city, String address, String date, String time, String fee, String istprize, String istrunnerup, String secondrunnerup, String notes) {
        this.name = name;
        this.sports = sports;
        this.state = state;
        this.city = city;
        this.address = address;
        this.date = date;
        this.time = time;
        this.fee = fee;
        this.istprize = istprize;
        this.istrunnerup = istrunnerup;
        this.secondrunnerup = secondrunnerup;
        this.notes = notes;
    }

    public Event() {
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSports() {
        return sports;
    }

    public void setSports(String sports) {
        this.sports = sports;
    }



    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getIstprize() {
        return istprize;
    }

    public void setIstprize(String istprize) {
        this.istprize = istprize;
    }

    public String getIstrunnerup() {
        return istrunnerup;
    }

    public void setIstrunnerup(String istrunnerup) {
        this.istrunnerup = istrunnerup;
    }

    public String getSecondrunnerup() {
        return secondrunnerup;
    }

    public void setSecondrunnerup(String secondrunnerup) {
        this.secondrunnerup = secondrunnerup;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
