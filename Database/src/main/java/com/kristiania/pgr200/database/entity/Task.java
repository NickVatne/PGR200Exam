package com.kristiania.pgr200.database.entity;

public class Task {
    int id;
    String title, description, status, firstUser, secondUser, thirdUser;

    public Task() {
    }

    public Task(String title, String description, String status, String firstUser, String secondUser, String thirdUser) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.firstUser = firstUser;
        this.secondUser = secondUser;
        this.thirdUser = thirdUser;
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

    public String getFirstUser() {
        return firstUser;
    }

    public void setFirstUser(String firstUser) {
        this.firstUser = firstUser;
    }

    public String getSecondUser() {
        return secondUser;
    }

    public void setSecondUser(String secondUser) {
        this.secondUser = secondUser;
    }

    public String getThirdUser() {
        return thirdUser;
    }

    public void setThirdUser(String thirdUser) {
        this.thirdUser = thirdUser;
    }
}
