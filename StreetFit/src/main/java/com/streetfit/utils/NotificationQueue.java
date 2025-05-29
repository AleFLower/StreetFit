package main.java.com.streetfit.utils;

import java.util.LinkedList;
import java.util.Queue;

import main.java.com.streetfit.model.TrainerNotification;

public class NotificationQueue {
    private static NotificationQueue instance;
    private final Queue<TrainerNotification> queue = new LinkedList<>();
    
    // Flag per vedere se la coda è stata configurata
    private static boolean isConfigured = false;

    private NotificationQueue() {}

    // Metodo di configurazione per inizializzare la coda, chiamato una sola volta
    public static synchronized void configure() {
        if (!isConfigured) {
            instance = new NotificationQueue();
            isConfigured = true;
        } else {
            throw new IllegalStateException("NotificationQueue has already been configured.");
        }
    }

    // Metodo per ottenere l'istanza della coda
    public static synchronized NotificationQueue getInstance() {
        if (!isConfigured) {
            throw new IllegalStateException("NotificationQueue must be configured before use.");
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
