package ru.otus.time;

import java.time.LocalDateTime;

public class TimeProvider {
    public LocalDateTime getTime(){
        return LocalDateTime.now();
    }
}
