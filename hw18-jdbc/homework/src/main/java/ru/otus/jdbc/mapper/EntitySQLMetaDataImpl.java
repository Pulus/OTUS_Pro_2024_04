package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class EntitySQLMetaDataImpl implements EntitySQLMetaData {
    private final MetaData<?> metaData;

    @Override
    public String getSelectAllSql() {
        return "SELECT * FROM " + metaData.getName();
    }

    @Override
    public String getSelectByIdSql() {
        Field id = metaData.getIdField();

        if (id == null) {
            throw new RuntimeException("No field with Id annotation");
        }

        return "SELECT * FROM " + metaData.getName() + " WHERE " + id.getName() + " = ?";
    }

    @Override
    public String getInsertSql() {
        List<String> fields =
                metaData.getFieldsWithoutId().stream().map(Field::getName).toList();

        String values = "?,".repeat(fields.size());
        values = values.substring(0, values.lastIndexOf(","));

        return "INSERT into " + metaData.getName() + " (" + String.join(", ", fields) + ") " + "VALUES (" + values
                + ")";
    }

    @Override
    public String getUpdateSql() {
        Field id = metaData.getIdField();

        if (id == null) {
            throw new RuntimeException("No field with Id annotation");
        }

        String values = metaData.getFieldsWithoutId().stream()
                .map(Field::getName)
                .map(f -> f + " = ?,")
                .collect(Collectors.joining());
        values = values.substring(0, values.lastIndexOf(","));

        return "UPDATE " + metaData.getName() + " SET " + values + " WHERE " + id.getName() + " = ?";
    }
}
