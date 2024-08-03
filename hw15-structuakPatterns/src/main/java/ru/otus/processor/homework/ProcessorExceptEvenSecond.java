package ru.otus.processor.homework;

import ru.otus.model.Message;
import ru.otus.processor.Processor;
import ru.otus.time.TimeManager;

public class ProcessorExceptEvenSecond implements Processor {
    private final TimeManager timeManager;

    public ProcessorExceptEvenSecond(TimeManager timeManager) {
        this.timeManager = timeManager;
    }

    @Override
    public Message process(Message message) {
        int second = timeManager.getSecond();

        if (second % 2 == 0) {
            throw new RuntimeException("An exception in an even second.");
        }

        return message;
    }
}
