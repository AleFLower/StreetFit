package main.java.com.streetfit.viewcli;

import java.io.BufferedReader;

import main.java.com.streetfit.beans.MessageBean;
import main.java.com.streetfit.beans.ParticipationBean;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import main.java.com.streetfit.beans.TrainingStageBean;

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

    public TrainingStageBean addstage() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String title = readNonEmptyInput(reader, "Enter the title of the stage: ", "Title cannot be empty.");
        String itinerary = readNonEmptyInput(reader, "Enter the workout itinerary: ", "Itinerary cannot be empty.");
        String category = readCategory(reader);
        Date date = readValidDate(reader, sdf);
        String place = readNonEmptyInput(reader, "Enter the place of the stage: ", "Place cannot be empty.");
        int maxParticipants = readPositiveInteger(reader, "Enter the maximum number of participants: ");

        TrainingStageBean stagebean = new TrainingStageBean(title, itinerary, category, date, place, maxParticipants);
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



    public void newNotifications(String message) {
        CLIHelper.displayNotification(message);
    }

    public void getCreatedStages(List<TrainingStageBean> stages) {
        CLIHelper.print("=========== Available Stages ===========");
        for (TrainingStageBean stage : stages) {
            CLIHelper.print("----------------------------------------");
            CLIHelper.print(" Title: " + stage.getTitle());
            CLIHelper.print(" Itinerary: " + stage.getItinerary());
            CLIHelper.print(" Category: " + stage.getCategory());
            CLIHelper.print(" Date: " + stage.getDate());
            CLIHelper.print(" Location: " + stage.getPlace());
            CLIHelper.print(" Max Participants: " + stage.getMaxParticipants());
            CLIHelper.print("----------------------------------------\n");
        }
    }
    

    public void retrieveMembers(List<ParticipationBean> participations) {
        CLIHelper.print("=========== Subscribed Members ===========");

        if (participations.isEmpty()) {
            CLIHelper.print("[No members enrolled]");
            return;
        }

        Map<String, Map<String, Integer>> userStageMap = new HashMap<>();

        for (ParticipationBean bean : participations) {
            userStageMap
                .computeIfAbsent(bean.getUsername(), k -> new HashMap<>())
                .merge(bean.getStage().getTitle(), bean.getTicket(), Integer::sum);
        }

        for (Map.Entry<String, Map<String, Integer>> entry : userStageMap.entrySet()) {
            CLIHelper.print("----------------------------------------");
            CLIHelper.print(" Username: " + entry.getKey());
            for (Map.Entry<String, Integer> stageEntry : entry.getValue().entrySet()) {
                CLIHelper.print("  • Stage: " + stageEntry.getKey());
                CLIHelper.print("    Tickets bought: " + stageEntry.getValue());
            }
            CLIHelper.print("----------------------------------------\n");
        }
    }

    


    public boolean requestMessages(List<MessageBean> messages) {
        if (messages == null || messages.isEmpty()) {
            CLIHelper.print("No messages to display.");
            return false;
        }

        CLIHelper.print("========= Messages =========");
        int count = 1;
        for (MessageBean msg : messages) {
            CLIHelper.print("Message #" + count++);
            CLIHelper.print("From:    " + msg.getFromUser());
            CLIHelper.print("Content: " + msg.getContent());

            if (msg.getReply() != null && !msg.getReply().isEmpty()) {
                CLIHelper.print("Reply:   " + msg.getReply());
            } else {
                CLIHelper.print("Reply:   [No reply yet]");
            }

            CLIHelper.print("-----------------------------");
        }

        CLIHelper.print("========= End of List =========");
        return true;
    }



    public void printSubscribers(List<TrainingStageBean> stageList, List<Integer> counters) {
        int i = 0;
        if (stageList.isEmpty()) {
            CLIHelper.print("[No stages enrolled]");
            return;
        }

        CLIHelper.print("Created stages:");
        for (TrainingStageBean stage : stageList) {
            if (counters.get(i) == 0) {
                CLIHelper.print(stage.getTitle() + "  -> Sold out");
            } else {
                CLIHelper.print(stage.getTitle() + "  -> Remaining tickets for this stage: " + counters.get(i));
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

