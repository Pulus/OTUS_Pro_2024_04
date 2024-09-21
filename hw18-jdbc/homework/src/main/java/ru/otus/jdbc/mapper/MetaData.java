package ru.otus.jdbc.mapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MetaData<T> {
    private String name;
    private Constructor<T> constructor;
    private Field idField;
    private List<Field> allFields;
    private List<Field> fieldsWithoutId;
}
