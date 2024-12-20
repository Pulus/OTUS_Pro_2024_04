package ru.otus.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import ru.otus.model.User;

public class InMemoryUserDao implements UserDao {
    private final Map<Long, User> users;

    public InMemoryUserDao() {
        users = new HashMap<>();
        users.put(1L, new User(1L, "Admin", "admin", "admin"));
    }

    @Override
    public Optional<User> findById(long id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return users.values().stream().filter(v -> v.getLogin().equals(login)).findFirst();
    }
}
