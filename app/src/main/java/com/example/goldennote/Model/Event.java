package com.example.goldennote.Model;

import java.sql.Date;

import javax.annotation.Nullable;

public class Event {


    private String id;

    private String date;

    private String title;

    private String description;



    public Event(String id,@Nullable String date, String title, String description) {
       this.id=id;
        this.date=date;
        this.title = title;
        this.description = description;
    }

    public Event() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
