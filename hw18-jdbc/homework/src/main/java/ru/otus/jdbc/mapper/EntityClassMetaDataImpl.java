package ru.otus.jdbc.mapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import lombok.AllArgsConstructor;
import ru.otus.crm.model.Id;

@AllArgsConstructor
public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {
    private final Class<T> actualClass;

    @Override
    public String getName() {
        return actualClass.getSimpleName();
    }

    @Override
    public Constructor<T> getConstructor() {
        try {
            return actualClass.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Field getIdField() {
        for (var field : actualClass.getDeclaredFields()) {
            Id annotation = field.getAnnotation(Id.class);
            if (annotation != null) {
                return field;
            }
        }

        return null;
    }

    @Override
    public List<Field> getAllFields() {
        return Arrays.stream(actualClass.getDeclaredFields()).toList();
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return Arrays.stream(actualClass.getDeclaredFields())
                .filter(field -> !field.equals(getIdField()))
                .toList();
    }

    @Override
    public MetaData<T> getAllMetaData() {
        return MetaData.<T>builder()
                .name(getName())
                .constructor(getConstructor())
                .idField(getIdField())
                .allFields(getAllFields())
                .fieldsWithoutId(getFieldsWithoutId())
                .build();
    }
}
