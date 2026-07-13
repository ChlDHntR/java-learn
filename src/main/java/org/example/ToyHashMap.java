package org.example;

class Human {
    private String name;
    private int age;

    public Human(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }
}

class Home {
    private String address;

    public Home(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }
}

class Node<K,V> {
    private K key;
    private V value;
    private Node<K,V> next;

    public Node(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public V get(K key) {
        return value;
    }

    public void setNext(Node<K,V> next) {
        this.next = next;
    }

    public Node<K, V> next() {
        return next;
    }
}

class HashMap<K,V> {
    private final Node<K,V>[] store = new Node[16];

    private int Hash(K key) {
        int hashed = key.hashCode();
        return hashed;
    }

    public void put(K key,V value) {
        if (store[Hash(key)] != null) {

        }
    }

    public V get(K key) {
    }

    public void resize() {

    }


}

public class ToyHashMap {
    private Human person1 = new Human("John", 36),
                  person2 = new Human("Andrew", 25);
    private Home home1 = new Home("NewYork"),
                 home2 = new Home("LA");
    private Node<Human, Home> node1 = new Node<>(person1, home1);

}
