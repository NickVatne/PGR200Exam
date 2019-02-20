package com.kristiania.pgr200.database.entity;

public class Task {
    int id , timemanagerId;
    String title, description, status;

    public Task() {
    }

    public Task(String title, String description, String status, int timemanagerId) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.timemanagerId = timemanagerId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTimemanagerId() {
        return timemanagerId;
    }

    public void setTimemanagerId(int timemanagerId) {
        this.timemanagerId = timemanagerId;
    }
}
