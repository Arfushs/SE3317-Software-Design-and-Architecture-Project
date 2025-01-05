package model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CompositeNotification implements Notification {
    private final List<Notification> notifications = new ArrayList<>();

    public void addNotification(Notification notification) {
        notifications.add(notification);
    }

    public void removeNotification(Notification notification) {
        notifications.remove(notification);
    }

    @Override
    public String getMessage() {
        return notifications.stream()
                .map(Notification::getMessage)
                .collect(Collectors.joining("\n"));
    }
}
