package model;

public class NotificationDecorator implements Notification {
    private final Notification baseNotification;
    private final String additionalMessage;

    public NotificationDecorator(Notification baseNotification, String additionalMessage) {
        this.baseNotification = baseNotification;
        this.additionalMessage = additionalMessage;
    }

    @Override
    public String getMessage() {
        // Temel bildirimin mesajını al ve ek mesajı ekle
        return baseNotification.getMessage() + "\n" + additionalMessage;
    }
}
