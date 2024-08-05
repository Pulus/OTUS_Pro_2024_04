package ru.otus.processor.homework;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.model.Message;
import ru.otus.time.TimeProvider;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProcessorTest {
    @Test
    @DisplayName("Исключение на четной секунде")
    void ExceptEvenSecond() {
        var timeManager = mock(TimeProvider.class);
        when(timeManager.getTime().getSecond()).thenReturn(2);

        var message = new Message.Builder(12L)
                .field1("field1")
                .build();

        var processor = new ProcessorExceptEvenSecond(timeManager);

        assertThat(processor.process(message)).isNotNull();
    }
}
