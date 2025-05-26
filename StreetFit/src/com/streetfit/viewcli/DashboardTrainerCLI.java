package com.streetfit.viewcli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.streetfit.beans.StageBean;
import com.streetfit.model.Message;
import com.streetfit.model.Participation;
import com.streetfit.model.TrainingStage;
import com.streetfit.utils.CLIHelper;

public class DashboardTrainerCLI {

    private  Scanner sc = new Scanner(System.in);

    public int showMenu() {
        int choice;

        CLIHelper.print("-----Welcome to StreetFit----");
        CLIHelper.print("1. Add new stage");
        CLIHelper.print("2. Members");
        CLIHelper.print("3. Created stages");
        CLIHelper.print("4. Q&A");
        CLIHelper.print("5. Logout");
        CLIHelper.print("Please, enter your choice: ");

        choice = sc.nextInt();
        sc.nextLine();  // Consumes newline character
        return choice;
    }

    public StageBean addstage() throws IOException {
        try {BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)) ;
            CLIHelper.print("Enter the title of the stage: ");
            String title = reader.readLine();

            CLIHelper.print("Enter the workout itinerary: ");
            String itinerary = reader.readLine();

            CLIHelper.print("Enter the category of the stage (Functional, Yoga, Dance, Stretching): ");
            String category = reader.readLine();

            CLIHelper.print("Enter the date of the stage (format: yyyy-MM-dd): ");
            String dateString = reader.readLine();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = null;
            try {
                date = sdf.parse(dateString);
            } catch (Exception e) {
                CLIHelper.printError("Invalid date format. Please enter the date in yyyy-MM-dd format.");
                return null;
            }

            CLIHelper.print("Enter the place of the stage: ");
            String place = reader.readLine();

            CLIHelper.print("Enter the maximum number of participants: ");
            int maxParticipants = Integer.parseInt(reader.readLine());

            StageBean stagebean = new StageBean(title, itinerary, category, date, place, maxParticipants);
            if (stagebean.isValid()) {
                return stagebean;
            }
        }catch(IOException e) {}
        
        return null;
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

    public void printMembers(List<Participation> p) {
        CLIHelper.print("=========== Subscribed Members ===========");

        if (p.isEmpty()) {
            CLIHelper.print("[No members enrolled]");
            return;
        }

        for (Participation member : p) {
            CLIHelper.print("----------------------------------------");
            CLIHelper.print(" Username: " + member.getUsername());
            CLIHelper.print(" Stage: " + member.getStage());
            CLIHelper.print(" Tickets bought: " + member.getTicket());
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

