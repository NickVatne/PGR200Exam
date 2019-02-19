package com.kristiania.pgr200.database.entity;

public class TaskManager {
    int id;
    String firstUser, SecondUser, ThirdUser;

    public TaskManager(){
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstUser() {
        return firstUser;
    }

    public void setFirstUser(String firstUser) {
        this.firstUser = firstUser;
    }

    public String getSecondUser() {
        return SecondUser;
    }

    public void setSecondUser(String secondUser) {
        SecondUser = secondUser;
    }

    public String getThirdUser() {
        return ThirdUser;
    }
    public void setThirdUser(String thirdUser) {
        ThirdUser = thirdUser;
    }
    @Override
    public String toString() {
        return "TaskManager{" +
                "id=" + id +
                ", firstUser='" + firstUser + '\'' +
                ", SecondUser='" + SecondUser + '\'' +
                ", ThirdUser='" + ThirdUser + '\'' +
                '}';
    }
}
