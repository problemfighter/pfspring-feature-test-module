package com.problemfighter.pfspring.module.kafka.model;

public class ProduceMessage {

    public Integer id;
    public String subject;
    public String message;

    public ProduceMessage() {
    }

    public ProduceMessage(Integer id, String subject, String message) {
        this.id = id;
        this.subject = subject;
        this.message = message;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ProduceMessage{" +
                "id=" + id +
                ", subject='" + subject + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
