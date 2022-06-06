package com.example.bigfamilyv20.SendNotificationPack;

public class Data {
    private String Title;
    private String message;

    public Data(String title, String message) {
        Title = title;
        this.message = message;
    }
    public Data() {

    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
