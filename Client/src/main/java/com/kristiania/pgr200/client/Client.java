package com.kristiania.pgr200.client;

import com.kristiania.pgr200.database.DatabaseHandler.DatabaseHandler;
import com.kristiania.pgr200.database.entity.Task;
import com.kristiania.pgr200.http.HttpRequest;
import com.kristiania.pgr200.http.HttpResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Client {

    private DatabaseHandler dh;
    Map<String, String> taskParameters;
    Map<String, String> updateTaskParameters;
    HttpResponse response;

    public static void main(String[] args) throws IOException, SQLException {
        new Client();
    }

    private Client() throws IOException, SQLException {
        //dh = new DatabaseHandler();
        int stopProgram = 0;
        int menu = 9;
        Scanner scanner = new Scanner(System.in);

        while (stopProgram < 1) {
            if (menu == 9) {
                System.out.println();
                System.out.println("Hello and Welcome to ProjectViewer");
                System.out.println("In this program you will be able to check the: ");
                System.out.println("(1) Name of the different Projects currently in Progress");
                System.out.println("(2) Create your own project");
                System.out.println("(3) Look at the different projects participants");
                System.out.println("(4) Change Tasks");
                System.out.println("(5) Exit Program");
                System.out.print("What do you want to do? ( 1 - 5): ");
            } else if (menu == 1) {
                //list All Talks
                System.out.println();
                System.out.println("----Listing all Projects----");
                HttpResponse response = new HttpRequest("GET", "localhost", 12080, "db/task").execute();
                System.out.println(response.getBody());
                System.out.println(("(Press 9 to go back to Main Menu)"));


            } else if (menu == 2) {
                System.out.println();
                System.out.println("----Create your own Project----");

                //Insert Talk to database
                taskParameters = createTaskParameters();
                String path = HttpRequest.createPath("/db/task", taskParameters);

                new HttpRequest("POST", "localhost", 12080, path).execute();

                System.out.println();
                System.out.println(("(Press 9 to go back to Main Menu)"));

            } else if (menu == 3) {
                System.out.println();
                System.out.println("Listing all participants in the different projects");
                response = new HttpRequest("GET", "localhost", 12080, "db/task").execute();
                System.out.println();
                System.out.println(response.getBody());

                //Update Title of talk to database
                updateTaskParameters = updateTaskParameters();


                String pathTitle = HttpRequest.createPath("/db/task/title", updateTaskParameters);
                String pathDesc = HttpRequest.createPath("/db/task/desc", updateTaskParameters);
                String pathStatus = HttpRequest.createPath("/db/task/status", updateTaskParameters);
                //Update Desc of talk to database

                if (updateTaskParameters.containsKey("title")) {
                    new HttpRequest("PUT", "localhost", 12080, pathTitle).execute();
                }
                if (updateTaskParameters.containsKey("desc")) {
                    new HttpRequest("PUT", "localhost", 12080, pathDesc).execute();

                }
                if (updateTaskParameters.containsKey("status")) {
                    new HttpRequest("PUT", "localhost", 12080, pathStatus).execute();

                }


            } else if (menu == 4) {
                System.out.println(" What project do you want to change? ");
                System.out.println(("(Press 9 to go back to Main Menu)"));
                System.out.println("");
            } else if (menu == 5) {
                System.out.println();
                System.out.println("Thank you for using ProjectViewer");
                stopProgram = 1;
            }
            if (stopProgram != 1) {
                menu = scanner.nextInt();
            }
        }
    }

    public Map<String, String> updateTaskParameters() throws SQLException {
        Map<String, String> parameters = new HashMap<>();

        Scanner input = new Scanner(System.in);

        List<Task> allAvailableTasks = dh.getAllAvailableTasks();

        System.out.print("Chose the ID of the task you wish to update: ");
        parameters.put("id", selectTask(input, allAvailableTasks));
        System.out.println();
        System.out.println("Do you wish to update the title, description, or status?");
        System.out.print("Enter here: ");

        String inputChoice = input.nextLine();

        if (inputChoice.equalsIgnoreCase("title")) {
            System.out.print("New title: ");
            String newTitle = input.nextLine();
            parameters.put("title", newTitle);
            System.out.println("Title updated - press 9 to go back to the menu");

            return parameters;
        } else if (inputChoice.equalsIgnoreCase("description")) {
            System.out.print("New description: ");
            String newDesc = input.nextLine();
            parameters.put("desc", newDesc);
            System.out.println("Description updated - press 9 to go back to the menu");

            return parameters;

        } else if (inputChoice.equalsIgnoreCase("status")) {
            System.out.print("Ny status: ");
            String newDesc = input.nextLine();
            parameters.put("status", newDesc);
            System.out.println("Status updated - press 9 to go back to the menu");


            return parameters;
        }
        return updateTaskParameters();
    }

    private String findTalkId(List<Task> allAvailableTalks, String selectedTalkId) {
        for (Task talk : allAvailableTalks) {
            if ((talk.getId() + "").equals(selectedTalkId)) {
                return talk.getId() + "";

            }
        }
        return null;
    }

    private Map<String, String> createTaskParameters() throws SQLException {
        Map<String, String> parameters = new HashMap<>();
        Scanner input = new Scanner(System.in);

        System.out.print("Add a Project Title: ");
        parameters.put("title", input.nextLine());

        System.out.print("Add a Project Description: ");
        parameters.put("description", input.nextLine());

        System.out.print("Add a Project Status ( Starting, Running, Finished, Delayed): ");
        parameters.put("status", input.nextLine());

        System.out.println("Add Taskmanager - 1:");
        parameters.put("first_user", input.nextLine());

        System.out.println("Add Taskmanager - 2:");
        parameters.put("second_user", input.nextLine());

        System.out.println("Add Taskmanager - 3:");
        parameters.put("third_user", input.nextLine());

        return parameters;
    }

    private String selectTask(Scanner input, List<Task> allAvailableTalks) {

        String selectedTalk = input.nextLine();
        String talkId = findTalkId(allAvailableTalks, selectedTalk);
        if (talkId == null) {
            System.out.println("Could not find the given task, please chose a task from the list.");
            selectTask(input, allAvailableTalks);
        }
        return talkId;
    }

}
