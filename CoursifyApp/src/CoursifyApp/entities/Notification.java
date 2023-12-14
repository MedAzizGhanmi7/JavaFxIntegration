/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CoursifyApp.entities;

import java.time.LocalDateTime;

/**
 *
 * @author hassa
 */
public class Notification {
    private long id;
    private String message;
    private LocalDateTime dateTime;
    private boolean read;

    public Notification(long id, String message, LocalDateTime dateTime, boolean read) {
        this.id = id;
        this.message = message;
        this.dateTime = dateTime;
        this.read = read;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }
}
