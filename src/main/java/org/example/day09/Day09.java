package org.example.day09;

import org.example.day08.Day8;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day09 {

    public ArrayList<Day8.Member> members = new ArrayList<>();

    public static void main() {
        Day8.Member user1 = new Day8.Member("John", 21, 1, true);
        Day8.Member user2 = new Day8.Member("Tom", 45, 4, false);
        Day8.Member user3 = new Day8.Member("Jane", 22, 2, true);
        Day8.Member user4 = new Day8.Member("Mark", 30, 2, false);
        Day8.Member user5 = new Day8.Member("Julie", 29, 2, false);
        Day8.Member user6 = new Day8.Member("Chris", 19, 0, false);

        ArrayList<Day8.Member> members = new ArrayList<Day8.Member>();
        members.add(user1);
        members.add(user2);
        members.add(user3);
        members.add(user4);
        members.add(user5);
        members.add(user6);

        //Loop
        ArrayList<Day8.Member> youngMembers1 = new ArrayList<Day8.Member>();
        for (Day8.Member member : members) {
            if (!Day8.isOld.test(member.age())) {
                youngMembers1.add(member);
            }
        }
        System.out.println(youngMembers1);

        //stream
        Stream<Day8.Member> youngMembers2 = members.stream()
                .filter(member -> !Day8.isOld.test(member.age()));
        ArrayList<Day8.Member> result = youngMembers2.collect(Collectors.toCollection(ArrayList::new));
        System.out.println(result);

    }

}
