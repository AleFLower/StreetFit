package com.streetfit.utils;


public class CLIHelper {
    // Method to print a message in a standardized format
    public static void print(String message) {
        System.out.println(message); // You can extend this later for more complex formatting
    }

    // Method to print an error message
    public static void printError(String message) {
        System.err.println(message);  // Use error stream for errors
    }

    // Method to print a message and wait for user input before proceeding
    public static void printAndWait(String message) {
        System.out.println(message);
        new java.util.Scanner(System.in).nextLine();  // Wait for user to press Enter
    }

    // Print notifications with a delay (like toast)
    public static void displayNotification(String message) {
        System.out.println(message);
        try {
            Thread.sleep(2000);  // Simulating toast for 2 seconds
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
