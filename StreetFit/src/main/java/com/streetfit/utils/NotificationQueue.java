package main.java.com.streetfit.utils;

import java.util.LinkedList;
import java.util.Queue;

import main.java.com.streetfit.model.TrainerNotification;

public class NotificationQueue {
    private static NotificationQueue instance;
    private final Queue<TrainerNotification> queue = new LinkedList<>();

    public static synchronized NotificationQueue getInstance() {
        if (instance == null) {
            instance = new NotificationQueue();
        }
        return instance;
    }

    public void addNotification(TrainerNotification n) {
        queue.add(n);
    }

    public Queue<TrainerNotification> getAndClearNotifications() {
        Queue<TrainerNotification> copy = new LinkedList<>(queue);
        queue.clear();
        return copy;
    }

    public boolean hasNotifications() {
        return !queue.isEmpty();
    }
}
