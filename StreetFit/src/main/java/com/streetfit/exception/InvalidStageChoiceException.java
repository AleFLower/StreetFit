package main.java.com.streetfit.exception;

public class InvalidStageChoiceException extends Exception {
    private final int choice;
    private final int maxIndex;

    public InvalidStageChoiceException(int choice, int maxIndex) {
        super("Invalid stage choice: " + choice);
        this.choice = choice;
        this.maxIndex = maxIndex;
    }

    public String getUserFriendlyMessage() {
        return String.format("Stage choice %d is invalid. Please select a value between 0 and %d.", choice, maxIndex - 1);
    }

    public int getChoice() {
        return choice;
    }

    public int getMaxIndex() {
        return maxIndex;
    }
}
