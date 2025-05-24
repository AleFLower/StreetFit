package com.streetfit.utils;

import java.util.LinkedList;
import java.util.Queue;

import com.streetfit.model.TrainerNotification;

public class NotificationQueue {
    private final Queue<TrainerNotification> queue = new LinkedList<>();

    private NotificationQueue() {}

    // Holder class: thread-safe, lazy loading garantito dalla JVM
    private static class Holder {
        private static final NotificationQueue INSTANCE = new NotificationQueue();
    }

    public static NotificationQueue getInstance() {
        return Holder.INSTANCE;
    }

    public synchronized void addNotification(TrainerNotification n) {
        queue.add(n);
    }

    public synchronized Queue<TrainerNotification> getAndClearNotifications() {
        Queue<TrainerNotification> copy = new LinkedList<>(queue);
        queue.clear();
        return copy;
    }

    public synchronized boolean hasNotifications() {
        return !queue.isEmpty();
    }
}


    public boolean hasNotifications() {
        return !queue.isEmpty();
    }
}
