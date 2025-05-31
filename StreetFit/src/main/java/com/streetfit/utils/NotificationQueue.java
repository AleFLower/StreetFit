package main.java.com.streetfit.utils;

import java.util.LinkedList;
import java.util.Queue;
import main.java.com.streetfit.model.TrainerNotification;

public class NotificationQueue {
    private final Queue<TrainerNotification> queue = new LinkedList<>();

    public NotificationQueue() {
        // Costruttore pubblico, così può essere istanziata ovunque serve
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
