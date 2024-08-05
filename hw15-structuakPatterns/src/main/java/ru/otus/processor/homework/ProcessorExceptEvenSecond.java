package ru.otus.processor.homework;

import ru.otus.model.Message;
import ru.otus.processor.Processor;
import ru.otus.time.TimeProvider;

public class ProcessorExceptEvenSecond implements Processor {
    private final TimeProvider timeProvider;

    public ProcessorExceptEvenSecond(TimeProvider timeProvider) {
        this.timeProvider = timeProvider;
    }

    @Override
    public Message process(Message message) {
        int second = timeProvider.getTime().getSecond();

        if (second % 2 == 0) {
            throw new RuntimeException("An exception in an even second.");
        }

        return message;
    }
}
