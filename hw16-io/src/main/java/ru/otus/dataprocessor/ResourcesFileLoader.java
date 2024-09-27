package ru.otus.dataprocessor;

import jakarta.json.*;

import java.util.ArrayList;
import java.util.List;

import jakarta.json.stream.JsonParser;
import ru.otus.model.Measurement;

public class ResourcesFileLoader implements Loader {
    private final String fileName;
    private static final List<Measurement> measurements = new ArrayList<>();

    public ResourcesFileLoader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Measurement> load() {
        // читает файл, парсит и возвращает результат
        try (var jsonParser =
                     Json.createParser(ResourcesFileLoader.class.getClassLoader().getResourceAsStream(fileName))) {
            String name = "";
            double value = 0;
            while (jsonParser.hasNext()) {
                JsonParser.Event event;
                event = jsonParser.next();
                switch (event) {
                    case VALUE_STRING -> name = jsonParser.getString();
                    case VALUE_NUMBER -> value = jsonParser.getBigDecimal().doubleValue();
                    case END_OBJECT -> measurements.add(new Measurement(name, value));
                }
            }
            return measurements;
        } catch (Exception e) {
            throw new FileProcessException(e);
        }
    }
}