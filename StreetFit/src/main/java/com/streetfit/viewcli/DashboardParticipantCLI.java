package main.java.com.streetfit.viewcli;

import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import main.java.com.streetfit.beans.HealthFormBean;
import main.java.com.streetfit.beans.TicketBean;
import main.java.com.streetfit.model.Message;
import main.java.com.streetfit.model.TrainingStage;
import main.java.com.streetfit.utils.CLIHelper;

public class DashboardParticipantCLI {

    private final Scanner sc = new Scanner(System.in);

    public int showMenu() {
        int choice = -1;

        while (true) {
            try {
                CLIHelper.print("----- Welcome to StreetFit -----");
                CLIHelper.print("1. Join a stage");
                CLIHelper.print("2. Your Q&A");
                CLIHelper.print("3. Search stage by keyword");
                CLIHelper.print("4. Stages you joined");
                CLIHelper.print("5. Logout");
                CLIHelper.print("Please enter your choice (1-5): ");

                choice = Integer.parseInt(sc.nextLine().trim());

                if (choice >= 1 && choice <= 5) {
                    return choice;
                } else {
                    CLIHelper.printError("Invalid choice. Please enter a number between 1 and 5.");
                }

            } catch (NumberFormatException e) {
                CLIHelper.printError("Invalid input. Please enter a numeric value.");
            }
        }
    }


    public String askSearchKeyword() {
        CLIHelper.print("Enter keyword (title/category/place): ");
        return sc.nextLine().trim();
    }

    public int printAllStages(List<TrainingStage> stages, List<Integer> list, List<Integer>remainings) {
        int choice;
        int i = 1;

        // Controlla se la lista stages è vuota
        if (stages.isEmpty()) {
            CLIHelper.print("No stages available.");
            return -1;
        }

        // Assicurati che list abbia la stessa dimensione di stages
        if (list.size() != stages.size()) {
            CLIHelper.print("Error: The list of availability is not aligned with the stages list.");
            return -1;
        }

        CLIHelper.print("\n=========== Available Stages ===========\n");

        // Itera su tutte le stage
        for (TrainingStage stage : stages) {
            if (list.get(i - 1) == 0) {  // Skip stages not available
                i++;
                continue;
            }

            CLIHelper.print("Stage " + i + ":");
            CLIHelper.print(" Title: " + stage.getTitle());
            CLIHelper.print(" Itinerary: " + stage.getItinerary());
            CLIHelper.print(" Category: " + stage.getCategory());
            CLIHelper.print(" Date: " + stage.getDate());
            CLIHelper.print(" Location: " + stage.getLocation());
            CLIHelper.print(" Tickets left: " + remainings.get(i-1) );
            CLIHelper.print("----------------------------------------------\n");
            i++;
        }

        CLIHelper.print("Please select a stage by number:");
        choice = sc.nextInt();
        choice -= 1;  // to match list index

        return choice;
    }
    
    public void printStages(List<TrainingStage> stages,
            List<Integer> remainings,
            List<Integer> bought) {
if (stages.isEmpty()) {
CLIHelper.print("You have not joined any stage.");
return;
}

CLIHelper.print("\n=========== Your Joined Stages ===========\n");

// Raggruppiamo per stage, evitando duplicati
Set<String> printedStages = new HashSet<>();
for (int i = 0; i < stages.size(); i++) {
TrainingStage stage = stages.get(i);

if (!printedStages.contains(stage.getTitle())) {
// Stampa solo se non è già stata stampata
printedStages.add(stage.getTitle());

CLIHelper.print("Stage " + (i + 1) + ":");
CLIHelper.print(" Title: " + stage.getTitle());
CLIHelper.print(" Itinerary: " + stage.getItinerary());
CLIHelper.print(" Category: " + stage.getCategory());
CLIHelper.print(" Date: " + stage.getDate());
CLIHelper.print(" Location: " + stage.getLocation());
CLIHelper.print(" Tickets bought: " + bought.get(i));
CLIHelper.print(" Tickets left:   " + remainings.get(i));
CLIHelper.print("----------------------------------------------\n");
}
}
}



    public HealthFormBean fillHealthForm() {
        CLIHelper.print("\n--- Fill in your Health Form ---");
        CLIHelper.print("Please answer yes(or y) or no(or n): ");

        CLIHelper.print("Do you have any injuries? : ");
        boolean hasInjuries = readYesNo();

        CLIHelper.print("Do you suffer from any heart issues? : ");
        boolean hasHeartIssues = readYesNo();

        CLIHelper.print("Are you currently on any medication? : ");
        boolean onMedication = readYesNo();

        return new HealthFormBean(hasInjuries, hasHeartIssues, onMedication);
    }

    private boolean readYesNo() {
        while (true) {
            String input = sc.nextLine().trim().toLowerCase();
            if (input.equals("yes") || input.equals("y")) return true;
            if (input.equals("no") || input.equals("n")) return false;
            CLIHelper.print("Please answer yes(or y) or no(or n): ");
        }
    }

    public TicketBean selectTicket() {
        String type;
        int qty;

        while (true) {
            CLIHelper.print("Choose your ticket type:");
            CLIHelper.print("base - Basic Ticket (10€)");
            CLIHelper.print("special - Includes T-shirt and bag (20€)");
            CLIHelper.print("vip - Includes T-shirt, bag, headphones, and shoes (35€)");
            CLIHelper.print("Ticket type: ");
            type = sc.nextLine().trim().toLowerCase();

            if (type.matches("base|special|vip")) break;
            CLIHelper.printError("Invalid ticket type. Please enter base, special, or vip.");
        }

        while (true) {
            CLIHelper.print("How many tickets? ");
            try {
                qty = Integer.parseInt(sc.nextLine());
                if (qty > 0) break;
                else CLIHelper.printError("Quantity must be a positive number.");
            } catch (NumberFormatException e) {
                CLIHelper.printError("Invalid input. Please enter a number.");
            }
        }

        return new TicketBean(type, qty);
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

    public String getMessage() {
        String input;

        // Chiedi se vuole scrivere un messaggio
        do {
            CLIHelper.print("Do you want to ask anything to the trainer? (yes/no)");
            input = sc.nextLine().trim().toLowerCase();
        } while (!input.equals("yes") && !input.equals("no"));

        // Se dice "no", non chiede il messaggio
        if (input.equals("no")) {
            return "";
        }

        // Se dice "yes", chiedi il messaggio da inviare
        CLIHelper.print("Enter your message for the trainer:");
        return sc.nextLine();
    }


    public void printTicketSummary(String description, double total) {
        CLIHelper.print("--- Ticket Summary ---");
        CLIHelper.print("Ticket: " + description);
        CLIHelper.print("Total: €" + total);
    }

    public void printMessage(String message) {
        CLIHelper.print(message);
    }
}

