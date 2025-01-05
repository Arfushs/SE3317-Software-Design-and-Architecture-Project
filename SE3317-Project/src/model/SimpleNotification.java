package model;

public class SimpleNotification implements Notification {
    private final String message;

    public SimpleNotification(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
