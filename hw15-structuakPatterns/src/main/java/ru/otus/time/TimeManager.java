package ru.otus.time;

import java.time.LocalDateTime;

public class TimeManager {
    public int getSecond(){
        return LocalDateTime.now().getSecond();
    }
}
