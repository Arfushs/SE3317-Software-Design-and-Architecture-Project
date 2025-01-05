package model;

import java.util.List;

public class NotificationDecorator implements Message {
    private final Message message;
    private final List<String> notifications;

    public NotificationDecorator(Message message, List<String> notifications) {
        this.message = message;
        this.notifications = notifications;
    }

    @Override
    public String getMessage() {
        String baseMessage = message.getMessage();
        if (!notifications.isEmpty()) {
            baseMessage += ", Notifications: " + String.join(", ", notifications);
        }
        return baseMessage;
    }
}
