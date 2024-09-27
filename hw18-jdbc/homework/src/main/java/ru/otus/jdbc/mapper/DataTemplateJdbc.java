package ru.otus.jdbc.mapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.DataTemplateException;
import ru.otus.core.repository.executor.DbExecutor;

/** Сохратяет объект в базу, читает объект из базы */
@SuppressWarnings("java:S1068")
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final MetaData<T> metaData;
    private final EntitySQLMetaData entitySQLMetaData;

    public DataTemplateJdbc(DbExecutor dbExecutor, MetaData<T> metaData) {
        this.dbExecutor = dbExecutor;
        this.metaData = metaData;
        this.entitySQLMetaData = new EntitySQLMetaDataImpl(metaData);
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), List.of(id), rs -> {
            try {
                if (rs.next()) {
                    return buildObject(rs);
                }

                return null;
            } catch (SQLException e) {
                throw new DataTemplateException(e);
            }
        });
    }

    @Override
    public List<T> findAll(Connection connection) {
        return dbExecutor
                .executeSelect(connection, entitySQLMetaData.getSelectAllSql(), Collections.emptyList(), rs -> {
                    var list = new ArrayList<T>();
                    try {
                        while (rs.next()) {
                            list.add(buildObject(rs));
                        }

                        return list;
                    } catch (SQLException e) {
                        throw new DataTemplateException(e);
                    }
                })
                .orElseThrow(() -> new RuntimeException("Unexpected error"));
    }

    @Override
    public long insert(Connection connection, T client) {
        List<Object> values = getValuesForQuery(client);

        return dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(), values);
    }

    @Override
    public void update(Connection connection, T client) {
        List<Object> values = getValuesForQuery(client);
        values.add(metaData.getIdField());

        dbExecutor.executeStatement(connection, entitySQLMetaData.getUpdateSql(), values);
    }

    private T buildObject(ResultSet rs) {
        try {
            Constructor<T> constructor = metaData.getConstructor();
            T object = constructor.newInstance();

            List<Field> allFields = metaData.getAllFields();
            for (Field field : allFields) {
                field.setAccessible(true);
                field.set(object, rs.getObject(field.getName()));
            }

            return object;
        } catch (SQLException e) {
            throw new DataTemplateException(e);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Object> getValuesForQuery(T client) {
        List<Object> values = new ArrayList<>();

        try {
            List<Field> fieldsWithoutId = metaData.getFieldsWithoutId();

            for (var field : fieldsWithoutId) {
                field.setAccessible(true);
                values.add(field.get(client));
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        return values;
    }
}
