package main.java.com.streetfit.controllercli;


import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import main.java.com.streetfit.beans.MessageBean;
import main.java.com.streetfit.beans.ParticipationBean;
import main.java.com.streetfit.beans.TrainingStageBean;
import main.java.com.streetfit.controller.AddStageController;
import main.java.com.streetfit.controller.JoinStageController;

import main.java.com.streetfit.model.TrainerNotification;

import main.java.com.streetfit.utils.NotificationQueue;
import main.java.com.streetfit.viewcli.DashboardTrainerCLI;
public class TrainerControllerCLI {  

    private DashboardTrainerCLI view = new DashboardTrainerCLI();  
    private JoinStageController joinController = new JoinStageController();
    private AddStageController addStagecontroller = new AddStageController();
    private NotificationQueue queue;

    public TrainerControllerCLI(NotificationQueue queue) {
        this.queue = queue;
    }

    public void start() {
        trainerLogin();
        while (true) {
            int choice = view.showMenu();
            switch (choice) {
                case 1 -> addstage();
                case 2 -> members();
                case 3 -> getNumberOfSub();
                case 4 -> handleMessages();
                case 5 -> { return; }
                default -> throw new IllegalArgumentException("Not a valid argument");
            }
        }
    }

    public void trainerLogin() {
        Queue<TrainerNotification> notifications = queue.getAndClearNotifications();

        if (notifications.isEmpty()) {
            view.printMessage("📭 No new notifications");
        } else {
            view.printMessage("📬 There are new notifications:");
            for (TrainerNotification n : notifications) {
                view.newNotifications(n.getMessage());
            }
        }
    }

    public void addstage() {
        try {
            TrainingStageBean stageBean = view.addstage();
            if (stageBean == null) throw new IllegalArgumentException("Invalid stage input");


            addStagecontroller.addstage(stageBean);

            // Convert model list to bean list
            List<TrainingStageBean> stages = addStagecontroller.getAllStages();
            

            view.getCreatedStages(stages);
        } catch (Exception e) {
            view.printMessage("❌ Error during stage creation: " + e.getMessage());
        }
    }

    public void members() {
        List<ParticipationBean> modelList = joinController.retrieveMembers();

        // Convert to beans
        List<ParticipationBean> beans = new ArrayList<>();
        for (ParticipationBean p : modelList) {
            beans.add(new ParticipationBean(p.getUsername(), p.getStage(), p.getTicket(),p.getTotal()));
        }

        view.retrieveMembers(beans);
        if (beans.isEmpty()) return;

        if (!view.askIfRemoveMember()) return;

        String username = view.askUsernameToRemove();
        String stage = view.askStageToRemove();

        try {
            joinController.removeParticipation(username, stage);
            view.printMessage("✅ Participation successfully removed.");
        } catch (Exception e) {
            view.printMessage("❌ Error removing participation: " + e.getMessage());
        }
    }

    public void getNumberOfSub() {
        List<TrainingStageBean> modelList = addStagecontroller.getAllStages();
        List<TrainingStageBean> beanList = new ArrayList<>();

        for (TrainingStageBean s : modelList) {
            beanList.add(new TrainingStageBean(
                s.getTitle(), s.getItinerary(), s.getCategory(),
                s.getDate(), s.getPlace(), s.getMaxParticipants()
            ));
        }

        List<Integer> counters = joinController.getSubscribers(beanList);

        view.printSubscribers(beanList, counters);
    }

    public void handleMessages() {
        List<MessageBean> modelMessages = joinController.retrieveMessages();

        // Convert model to bean
        List<MessageBean> beanMessages = new ArrayList<>();
        for (MessageBean m : modelMessages) {
            beanMessages.add(new MessageBean(m.getFromUser(), m.getContent(), m.getReply()));
        }

        boolean hasMessages = view.requestMessages(beanMessages);
        if (!hasMessages) return;

        int choice = view.replyToMessages();
        if (choice == 0) return;

        if (choice < 1 || choice > modelMessages.size()) {
            view.printMessage("Invalid message selection.");
            return;
        }

        MessageBean selected = beanMessages.get(choice - 1);
        String reply = view.askReplyContent();
        joinController.updateMessage(selected, reply);
    }
}
