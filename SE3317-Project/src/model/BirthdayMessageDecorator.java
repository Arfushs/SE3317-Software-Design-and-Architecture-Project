package model;

public class BirthdayMessageDecorator implements Notification {
    private final Notification baseNotification;
    private final String userBirthday;

    public BirthdayMessageDecorator(Notification baseNotification, String userBirthday) {
        this.baseNotification = baseNotification;
        this.userBirthday = userBirthday;
    }

    @Override
    public String getMessage() {
        String baseMessage = baseNotification.getMessage();
        String today = new java.text.SimpleDateFormat("MM-dd").format(new java.util.Date());
        if (today.equals(userBirthday)) {
            return baseMessage + "\nHappy Birthday!";
        }
        return baseMessage;
    }
}
