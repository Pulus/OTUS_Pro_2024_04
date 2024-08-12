package ru.otus.dataprocessor;

import jakarta.json.Json;

import java.io.*;
import java.util.Map;

public class FileSerializer implements Serializer {
    private final String fileName;

    public FileSerializer(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void serialize(Map<String, Double> data) {
        // формирует результирующий json и сохраняет его в файл
        try (var output = Json.createGenerator(new FileWriter(fileName))) {
            output.writeStartObject();
            data.forEach(output::write);
            output.writeEnd();
        } catch (Exception e) {
            throw new FileProcessException(e);
        }
    }
}
