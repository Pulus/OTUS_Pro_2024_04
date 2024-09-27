package ru.otus.jdbc.mapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import ru.otus.crm.model.Id;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {
    private final Class<T> actualClass;
    private final List<Field> declaredFields;
    private final List<Field> fieldsWithoutId;

    public EntityClassMetaDataImpl(Class<T> actualClass){
        this.actualClass = actualClass;
        this.declaredFields = Arrays.stream(actualClass.getDeclaredFields()).toList();
        this.fieldsWithoutId = declaredFields.stream().filter(field -> !field.equals(getIdField())).toList();
    }
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
        for (var field : declaredFields) {
            Id annotation = field.getAnnotation(Id.class);
            if (annotation != null) {
                return field;
            }
        }
        return null;
    }

    @Override
    public List<Field> getAllFields() {
        return declaredFields;
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return fieldsWithoutId;
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
