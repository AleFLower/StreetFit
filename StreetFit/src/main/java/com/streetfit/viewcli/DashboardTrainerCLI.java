package main.java.com.streetfit.viewcli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import main.java.com.streetfit.beans.StageBean;
import main.java.com.streetfit.model.Message;
import main.java.com.streetfit.model.Participation;
import main.java.com.streetfit.model.TrainingStage;
import main.java.com.streetfit.utils.CLIHelper;

public class DashboardTrainerCLI {

    private  Scanner sc = new Scanner(System.in);

    public int showMenu() {
        int choice = -1;

        while (true) {
            try {
                CLIHelper.print("-----Welcome to StreetFit----");
                CLIHelper.print("1. Add new stage");
                CLIHelper.print("2. Members");
                CLIHelper.print("3. Created stages");
                CLIHelper.print("4. Q&A");
                CLIHelper.print("5. Logout");
                CLIHelper.print("Please, enter your choice: ");

                choice = Integer.parseInt(sc.nextLine().trim());

                if (choice >= 1 && choice <= 5) {
                    return choice;
                } else {
                    CLIHelper.printError("Not a valid choise. Please insert a number from  1 to 5.");
                }

            } catch (NumberFormatException e) {
                CLIHelper.printError("Not a valid input. Please insert only numbers.");
            }
        }
    }

    public StageBean addstage() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String title = readNonEmptyInput(reader, "Enter the title of the stage: ", "Title cannot be empty.");
        String itinerary = readNonEmptyInput(reader, "Enter the workout itinerary: ", "Itinerary cannot be empty.");
        String category = readCategory(reader);
        Date date = readValidDate(reader, sdf);
        String place = readNonEmptyInput(reader, "Enter the place of the stage: ", "Place cannot be empty.");
        int maxParticipants = readPositiveInteger(reader, "Enter the maximum number of participants: ");

        StageBean stagebean = new StageBean(title, itinerary, category, date, place, maxParticipants);
        return stagebean.isValid() ? stagebean : null;
    }

    private String readNonEmptyInput(BufferedReader reader, String prompt, String errorMsg) throws IOException {
        String input;
        do {
            CLIHelper.print(prompt);
            input = reader.readLine();
            if (input != null && !input.trim().isEmpty()) {
                return input.trim();
            }
            CLIHelper.printError(errorMsg);
        } while (true);
    }

    private String readCategory(BufferedReader reader) throws IOException {
        String input;
        do {
            CLIHelper.print("Enter the category of the stage (Functional, Yoga, Dance, Stretching): ");
            input = reader.readLine().trim();
            if (input.matches("(?i)Functional|Yoga|Dance|Stretching")) {
                return input;
            }
            CLIHelper.printError("Invalid category. Allowed: Functional, Yoga, Dance, Stretching.");
        } while (true);
    }

    private Date readValidDate(BufferedReader reader, SimpleDateFormat sdf) throws IOException {
        do {
            CLIHelper.print("Enter the date of the stage (format: yyyy-MM-dd): ");
            try {
                return sdf.parse(reader.readLine());
            } catch (Exception e) {
                CLIHelper.printError("Invalid date format. Try again (yyyy-MM-dd).");
            }
        } while (true);
    }

    private int readPositiveInteger(BufferedReader reader, String prompt) throws IOException {
        do {
            CLIHelper.print(prompt);
            try {
                int value = Integer.parseInt(reader.readLine());
                if (value > 0) {
                    return value;
                } else {
                    CLIHelper.printError("Number must be positive.");
                }
            } catch (NumberFormatException e) {
                CLIHelper.printError("Invalid number. Please enter a valid integer.");
            }
        } while (true);
    }



    public void displayNotification(String message) {
        CLIHelper.displayNotification(message);
    }

    public void printAllStages(List<TrainingStage> stages) {
        CLIHelper.print("=========== Available Stages ===========");
        for (TrainingStage stage : stages) {
            CLIHelper.print("----------------------------------------");
            CLIHelper.print(" Title: " + stage.getTitle());
            CLIHelper.print(" Itinerary: " + stage.getItinerary());
            CLIHelper.print(" Category: " + stage.getCategory());
            CLIHelper.print(" Date: " + stage.getDate());
            CLIHelper.print(" Location: " + stage.getLocation());
            CLIHelper.print(" Max Participants: " + stage.getMaxParticipants());
            CLIHelper.print("----------------------------------------\n");
        }
    }

    public void printMembers(List<Participation> participations) {
        CLIHelper.print("=========== Subscribed Members ===========");

        if (participations.isEmpty()) {
            CLIHelper.print("[No members enrolled]");
            return;
        }

        // Mappa username → lista delle sue partecipazioni
        Map<String, List<Participation>> grouped = new HashMap<>();

        for (Participation member : participations) {
            grouped.computeIfAbsent(member.getUsername(), k -> new ArrayList<>()).add(member);
        }

        // Stampa ogni utente e le sue partecipazioni, raggruppando per stage
        for (Map.Entry<String, List<Participation>> entry : grouped.entrySet()) {
            String username = entry.getKey();
            List<Participation> userParticipations = entry.getValue();

            // Mappa per raggruppare i ticket per stage
            Map<String, Integer> stageTickets = new HashMap<>();
            for (Participation participation : userParticipations) {
                String stage = participation.getStage();
                int tickets = participation.getTicket();
                stageTickets.put(stage, stageTickets.getOrDefault(stage, 0) + tickets);
            }

            CLIHelper.print("----------------------------------------");
            CLIHelper.print(" Username: " + username);

            for (Map.Entry<String, Integer> stageEntry : stageTickets.entrySet()) {
                CLIHelper.print("  • Stage: " + stageEntry.getKey());
                CLIHelper.print("    Tickets bought: " + stageEntry.getValue());
            }

            CLIHelper.print("----------------------------------------\n");
        }
    }



    public boolean printMessages(List<Message> messages) {
        if (messages == null || messages.isEmpty()) {
            CLIHelper.print("No messages to display.");
            return false;
        }

        CLIHelper.print("========= Messages =========");

        int count = 1;
        for (Message msg : messages) {
            CLIHelper.print("Message #" + count++);
            CLIHelper.print("From:    " + msg.getFromUser());
            CLIHelper.print("Content: " + msg.getContent());

            if (msg.hasReply()) {
                CLIHelper.print("Reply:   " + msg.getReply());
            } else {
                CLIHelper.print("Reply:   [No reply yet]");
            }

            CLIHelper.print("-----------------------------");
        }

        CLIHelper.print("========= End of List =========");
        return true;
    }

 // Inside DashboardTrainerCLI class

    public void printSubscribers(List<TrainingStage> stageList, List<Integer> counters) {
        int i = 0;
        if (stageList.isEmpty()) {
            CLIHelper.print("[No stages enrolled]");
            return;
        }
        CLIHelper.print("Created stages:");
        for (TrainingStage stage : stageList) {
            if (counters.get(i) == 0) {
                CLIHelper.print(stage.getTitle() + "  " + " -> Sold out");
            } else {
                CLIHelper.print(stage.getTitle() + "  " + " -> remaining tickets for this stage: " + counters.get(i));
            }
            i++;
        }
    }

    public int replyToMessages() {
        CLIHelper.print("Select a message you want to reply to(0 to exit): ");
        int choice = sc.nextInt();
        sc.nextLine();  // Consume newline
        return choice;
    }

    public String askReplyContent() {
        CLIHelper.print("Enter your reply: ");
        return sc.nextLine();
    }

    public void printMessage(String message) {
        CLIHelper.print(message);
    }

    public boolean askIfRemoveMember() {
        CLIHelper.print("Do you want to remove a participation? (y/n): ");
        String input = sc.nextLine();
        return input.equalsIgnoreCase("y");
    }

    public String askUsernameToRemove() {
        CLIHelper.print("Enter the username to remove: ");
        return sc.nextLine().trim();
    }

    public String askStageToRemove() {
        CLIHelper.print("Enter the stage title: ");
        return sc.nextLine().trim();
    }
}

