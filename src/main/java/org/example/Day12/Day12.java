package org.example.Day12;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

public class Day12 {
    public static void main(String[] args) {
        Path path = Paths.get("src/main/resources/members.csv");
        ArrayList<Member> members = new ArrayList<>();

        try (Stream<String> file = Files.lines(path)) {
            file.skip(1)
                    .limit(5)
                    .forEach(line -> {
                        System.out.println(line);
                        String[] column = line.split("\\s+");
                        System.out.println(column[1]);
                        members.add(new Member(column[1], Integer.parseInt(column[2]), column[3]));
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
        members.forEach(i -> System.out.println(i));
    }
}

class Member {
    private String name;
    private int age;
    private String job;

    public Member(String name, int age, String job) {
        this.name = name;
        this.age = age;
        this.job = job;
    }

    @Override
    public String toString() {
        return name + " age: " + age + " job: " + job;
    }



}
