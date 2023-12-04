package org.rmj.guanzongroup.gsecurity.pojo.notification;

public class Notification {

    private final String messageTitle;
    private final String messageBody;
    private final String messageDate;
    private final String messageAuthor;
    private final String messageType;

    public Notification(String messageTitle, String messageBody, String messageDate, String messageAuthor, String messageType) {
        this.messageTitle = messageTitle;
        this.messageBody = messageBody;
        this.messageDate = messageDate;
        this.messageAuthor = messageAuthor;
        this.messageType = messageType;
    }

    public String getMessageTitle() {
        return messageTitle;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public String getMessageDate() {
        return messageDate;
    }

    public String getMessageAuthor() {
        return messageAuthor;
    }

    public String getMessageType() {
        return messageType;
    }
}
