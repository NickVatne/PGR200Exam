package com.kristiania.pgr200.client;

import com.kristiania.pgr200.database.DatabaseHandler.DatabaseHandler;
import com.kristiania.pgr200.database.entity.Task;
import com.kristiania.pgr200.database.entity.TaskManager;
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
    Map<String, String> talkParameters;
    Map<String, String> updateTalkParameters;
    HttpResponse response;

    public static void main(String[] args) throws IOException, SQLException {
        new Client();
    }

    private Client() throws IOException, SQLException {
        dh = new DatabaseHandler();
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
                System.out.println("(3) Look at the differnt projects participants");
                System.out.println("(4) Change Tasks");
                System.out.println("(5) Exit Program");
                System.out.print("What do you want to do? ( 1 - 5): ");
            } else if (menu == 1) {
                //list All Talks
                System.out.println();
                System.out.println("----Listing all Projects----");
                HttpResponse response = new HttpRequest("GET", "localhost", 0, "db/task").execute();
                System.out.println(response.getBody());
                System.out.println(("(Press 9 to go back to Main Menu)"));


            } else if (menu == 2) {
                System.out.println();
                System.out.println("----Create your own Project----");

                //Insert Talk to database
                talkParameters = createTalkParameters();
                String path = HttpRequest.createPath("/db/task", talkParameters);

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
                updateTalkParameters = updateTalkParameters();


                String pathTitle = HttpRequest.createPath("/db/task/title", updateTalkParameters);
                String pathDesc = HttpRequest.createPath("/db/task/desc", updateTalkParameters);
                String pathStatus = HttpRequest.createPath("/db/task/status", updateTalkParameters);
                //Update Desc of talk to database

                if (updateTalkParameters.containsKey("title")) {
                    new HttpRequest("PUT", "localhost", 12080, pathTitle).execute();
                }
                if (updateTalkParameters.containsKey("desc")) {
                    new HttpRequest("PUT", "localhost", 12080, pathDesc).execute();

                }
                if (updateTalkParameters.containsKey("status")) {
                    new HttpRequest("PUT", "localhost", 12080, pathStatus).execute();

                }


            } else if (menu == 4) {
                System.out.println(" What project do you want to change? ");
                System.out.println(("(Press 9 to go back to Main Menu)"));
                System.out.println("");
            } else if (menu == 5){
                System.out.println();
                System.out.println("Thank you for using ProjectViewer");
                stopProgram = 1;
            }
            if (stopProgram != 1) {
                menu = scanner.nextInt();
            }
        }
    }

    public Map<String, String> updateTalkParameters() throws SQLException {
        Map<String, String> parameters = new HashMap<>();

        Scanner input = new Scanner(System.in);

        List<Task> allAvailableTasks = dh.getAllAvailableTasks();

        System.out.print("Velg ID på konferanse du vil oppdatere: ");
        parameters.put("id", selectTask(input, allAvailableTasks));
        System.out.println();
        System.out.println("Vil du oppdatere tittel, beskrivelse eller status?");
        System.out.print("Valg: ");

        String inputChoice = input.nextLine();

        if (inputChoice.equalsIgnoreCase("tittel")) {
            System.out.print("Ny tittel: ");
            String newTitle = input.nextLine();
            parameters.put("title", newTitle);
            System.out.println("Tittel oppdatert - trykk 42 for å se meny");

            return parameters;
        } else if (inputChoice.equalsIgnoreCase("beskrivelse")) {
            System.out.print("Ny beskrivelse: ");
            String newDesc = input.nextLine();
            parameters.put("desc", newDesc);
            System.out.println("Beskrivelse oppdatert - trykk 42 for å se meny");

            return parameters;

        } else if (inputChoice.equalsIgnoreCase("status")) {
            System.out.print("Ny status: ");
            String newDesc = input.nextLine();
            parameters.put("status", newDesc);
            System.out.println("Beskrivelse oppdatert - trykk 42 for å se meny");


            return parameters;
        }
        return updateTalkParameters();
    }

    private String findTalkId(List<Task> allAvailableTalks, String selectedTalkId) {
        for (Task talk : allAvailableTalks) {
            if ((talk.getId() + "").equals(selectedTalkId)) {
                return talk.getId() + "";

            }
        }
        return null;
    }

    private Map<String, String> createTalkParameters() throws SQLException {
        Map<String, String> parameters = new HashMap<>();
        Scanner input = new Scanner(System.in);

        System.out.print("Legg til foredragtittel: ");
        parameters.put("title", input.nextLine());

        System.out.print("Legg til beskrivelse: ");
        parameters.put("description", input.nextLine());

        System.out.print("Legg til status: ");
        parameters.put("status", input.nextLine());

        List<TaskManager> allAvailableTM = dh.getAllAvailableTaskManagers();
        parameters.put("time", selectTM(input, allAvailableTM));

        return parameters;
    }

    private String selectTask(Scanner input, List<Task> allAvailableTalks) {

        String selectedTalk = input.nextLine();
        String talkId = findTalkId(allAvailableTalks, selectedTalk);
        if (talkId == null) {
            System.out.println("Fant ikke Foredrag, vennligst velg et foredrag fra listen");
            selectTask(input, allAvailableTalks);
        }
        return talkId;
    }

    private String selectTM(Scanner input, List<TaskManager> allAvailableTM) {
        System.out.print("Skriv kategori på konferansen: ");
        System.out.println(allAvailableTM.toString());
        System.out.println();
        System.out.println("Kategori:");

        String selectedTM = input.nextLine();
        String topicId = findTMId(allAvailableTM, selectedTM);
        if (topicId == null) {
            System.out.println("Fant ikke kategori, vennligst velg tilgjengelig kategori");
            selectTM(input, allAvailableTM);
        }
        return topicId;
    }

    private String findTMId(List<TaskManager> allAvailableTM, String selectedTM) {
        for (TaskManager tm : allAvailableTM) {
            if ((tm.getFirstUser().equalsIgnoreCase(selectedTM)) || (tm.getSecondUser().equalsIgnoreCase(selectedTM)) || (tm.getThirdUser().equalsIgnoreCase(selectedTM))) {
                return tm.getId() + "";
            }
        }
        return null;
    }

}
