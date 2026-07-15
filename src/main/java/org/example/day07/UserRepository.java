package org.example.day07;

import java.util.HashMap;
import java.util.Objects;

public class UserRepository {
    private final HashMap<String,User> store = new HashMap<>();

    //key by name instead
    public void create(String name, int age) throws DupUserException {
        User findUser = store.get(name);

        if (findUser != null) {
            throw new DupUserException("User already exist");
        }
        User newUser = new User(name, age);
        store.put(name, newUser);
    }

    public User findByName(String name) throws User404Exception {
        User user = store.get(name);
        if (user != null) {
            return user;
        } else {
            throw new User404Exception("No user found with name " + name);
        }
    }

    public void update(String name, String newName, int newAge) throws User404Exception {
        User user = store.get(name);
        if (user != null) {
            User updatedUser = new User(newName, newAge);
            store.remove(name);
            store.put(newName, updatedUser);
        } else {
            throw new User404Exception("No user found with name " + name);
        }
    }

    public void delete(String name) throws User404Exception {
        User user = store.get(name);
        if (user != null) {
            store.remove(name);
        } else {
            throw new User404Exception("No user found with name " + name);
        }
    }

}

class User {
    private final String name;
    private final int age;

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        User user = (User) object;

        if ((user.getName().equals(this.name)) && (user.getAge() == this.age)) {
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


