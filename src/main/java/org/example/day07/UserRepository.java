package org.example.day07;

import java.util.HashMap;
import java.util.Objects;

public class UserRepository {
    private final HashMap<Integer,User> store = new HashMap<>();

    //key by name instead
    public void create(String name, int age) throws DupUserException {
        User newUser = new User(name, age);
        int hashed = newUser.hashCode();
        User findUser = store.get(hashed);

        if (findUser != null) {
            throw new DupUserException("User already exist");
        }
        store.put(hashed, newUser);
    }

    public User findByNameAndAge(String name, int age) throws User404Exception {
        User user = store.get(name.hashCode());
        if (user != null) {
            return user;
        }
        throw new User404Exception("No user found with name " + name);
    }

    public void update(String name, String newName, int newAge) throws User404Exception {
        User user = store.get(name.hashCode());
        if (user != null) {
            User updatedUser = new User(newName, newAge);
            store.put(name.hashCode(), updatedUser);
        }
        throw new User404Exception("No user found with name " + name);
    }

    public void delete(String name) throws User404Exception {
        User user = store.get(name.hashCode());
        if (user != null) {
            store.remove(name.hashCode());
        }
        throw new User404Exception("No user found with name " + name);
    }

}

class User {
    private String name;
    private int age;

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        User user = (User) object;

        if (user.getName() == this.name && user.getAge() == this.age) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return this.name;
    }
    public int getAge() {
        return this.age;
    }
}

class DupUserException extends Exception {
    public DupUserException(String str) {
        super(str);
    }
}
class User404Exception extends Exception {
    public User404Exception(String str) {
        super(str);
    }
}


