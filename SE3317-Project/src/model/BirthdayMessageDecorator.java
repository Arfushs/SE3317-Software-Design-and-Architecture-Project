package model;

public class BirthdayMessageDecorator implements Message {
    private final Message message;
    private final String userBirthday;

    public BirthdayMessageDecorator(Message message, String userBirthday) {
        this.message = message;
        this.userBirthday = userBirthday;
    }

    @Override
    public String getMessage() {
        String baseMessage = message.getMessage();
        String today = new java.text.SimpleDateFormat("MM-dd").format(new java.util.Date());
        if (today.equals(userBirthday)) {
            baseMessage += ", Happy Birthday!";
        }
        return baseMessage;
    }
}
