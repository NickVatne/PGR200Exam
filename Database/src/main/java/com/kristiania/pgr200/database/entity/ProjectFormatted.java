package com.kristiania.pgr200.database.entity;

public class ProjectFormatted {
    private static final String COLUMNFORMAT = "%-3s %-40s %-100s %-8s %-15s %-15s %-15s\n";
    int id;
    String title, description, status, first_user, second_user, third_user;

    public ProjectFormatted(int id, String title, String description, String status, String first_user, String second_user, String third_user) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.first_user = first_user;
        this.second_user = second_user;
        this.third_user = third_user;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public String getFirst_user() {
        return first_user;
    }

    public String getSecond_user() {
        return second_user;
    }

    public String getThird_user() {
        return third_user;
    }
    public static String createHeaderLine(){
        return String.format(COLUMNFORMAT, "|ID|", "|TITLE|", "|DESCRIPTION|", "|STATUS|", "|FIRST_USER|", "|SECOND_USER|", "|THIRD_USER|");
    }
}
