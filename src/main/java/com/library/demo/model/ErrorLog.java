package com.library.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "error_logs")
public class ErrorLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String exceptionMessage;

    @Column(length = 2000) // Limit the database column length to avoid data truncation issues
    private String stackTrace;

    private Integer patreonId;

    private String guid;

    private LocalDateTime timestamp;

    // Constructors, getters, and setters
    public ErrorLog() {
        this.timestamp = LocalDateTime.now();
        this.guid = java.util.UUID.randomUUID().toString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        // Truncate the stack trace if it exceeds 2000 characters
        if (stackTrace != null && stackTrace.length() > 2000) {
            this.stackTrace = stackTrace.substring(0, 2000);
        } else {
            this.stackTrace = stackTrace;
        }
    }

    public Integer getPatreonId() {
        return patreonId;
    }

    public void setPatreonId(Integer patreonId) {
        this.patreonId = patreonId;
    }

    public String getGuid() {
        return guid;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }
}
