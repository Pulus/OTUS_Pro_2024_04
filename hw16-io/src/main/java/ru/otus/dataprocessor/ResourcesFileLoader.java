package ru.otus.dataprocessor;

import jakarta.json.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        try (var jsonReader =
                     Json.createReader(ResourcesFileLoader.class.getClassLoader().getResourceAsStream(fileName))) {
            navigateTree(jsonReader.read());
            return measurements;
        } catch (Exception e) {
            throw new FileProcessException(e);
        }
    }

    private static void navigateTree(JsonValue tree) {
        switch (tree.getValueType()) {
            case OBJECT -> {
                String name = "";
                double value = 0;

                var jsonObject = (JsonObject) tree;
                for (Map.Entry<String, JsonValue> entry : jsonObject.entrySet()) {
                    switch (entry.getKey()) {
                        case "name" -> name = entry.getValue().toString().replaceAll("\"", "");
                        case "value" -> {
                            JsonNumber num = (JsonNumber) entry.getValue();
                            value = num.doubleValue();
                        }
                    }
                }
                measurements.add(new Measurement(name, value));
            }
            case ARRAY -> {
                JsonArray array = (JsonArray) tree;
                for (JsonValue val : array) {
                    navigateTree(val);
                }
            }
            default -> throw new FileProcessException("Unexpected value: " + tree.getValueType());
        }
    }
}
