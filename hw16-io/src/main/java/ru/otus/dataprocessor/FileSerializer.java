package ru.otus.dataprocessor;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.Map;

public class FileSerializer implements Serializer {
    private final String fileName;

    public FileSerializer(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void serialize(Map<String, Double> data) {
        // формирует результирующий json и сохраняет его в файл
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        data.forEach(objectBuilder::add);
        JsonObject build = objectBuilder.build();

        try (var output = Json.createWriter(new BufferedOutputStream(new FileOutputStream(fileName)))) {
            output.writeObject(build);
        } catch (Exception e) {
            throw new FileProcessException(e);
        }
    }
}
