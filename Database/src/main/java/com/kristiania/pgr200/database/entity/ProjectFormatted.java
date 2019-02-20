package com.kristiania.pgr200.database.entity;

public class ProjectFormatted {
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

    public String formatOutput(){
        String output = "\n ID: "+id + "\n Task: "+title+"\n Description: "+ description + "\n Project Status: "+ status + "\n Participants: " + first_user +  ", " + second_user + ", " + third_user+ "\n";
        return output;
    }
}
