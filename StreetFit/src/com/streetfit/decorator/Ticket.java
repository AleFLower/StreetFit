package com.streetfit.decorator;

public interface Ticket {
    double getPrice();
    String getDescription();
    int getQuantity();
    void setQuantity(int quantity);
}