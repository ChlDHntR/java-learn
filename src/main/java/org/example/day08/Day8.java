package org.example.day08;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class Day8 {

    //Anonymous-class
    Consumer<String> toUpperCase = new Consumer<>() {
        @Override
        public void accept(String o) {
            System.out.println(o.toUpperCase());
        }
    };
    BiConsumer<String, String> connectStr = new BiConsumer<String, String>() {
        @Override
        public void accept(String s, String s2) {
            System.out.println(s + s2);
        }
    };
    Function<Integer, Integer> squareInt = new Function<Integer, Integer>() {
        @Override
        public Integer apply(Integer integer) {
            return integer*integer;
        }
    };
    Predicate<Integer> isEven = new Predicate<Integer>() {
        @Override
        public boolean test(Integer i) {
            return i%2 == 0;
        }
    };

    //Lambda
    Consumer<String> toUpperCaseLambda =  (String s) -> System.out.println(s);
    BiConsumer<String, String> connectStrLambda = (String s, String s2) -> System.out.println(s+s2);
    Function<Integer, Integer> squareIntLambda = (Integer i) -> i*i;
    Predicate<Integer> isEvenLambda = i -> i%2 == 0;
    Function<Integer, Integer> plus1 = i -> i +1;

    public Function<Integer, Integer> andThenTest = squareIntLambda.andThen(plus1);
    public Function<Integer, Integer> composeTest = squareIntLambda.compose(plus1);

    //Predicate chaining
    public record Member(String name, int age, int level, boolean isGay) {}

    public Member user1 = new Member("John", 21, 1, true);
    public Member user2 = new Member("Tom", 45, 4, false);
    public Member user3 = new Member("Jane", 22, 2, true);
    public Member user4 = new Member("Mark", 30, 2, false);
    public Member user5 = new Member("Julie", 29, 2, false);
    public Member user6 = new Member("Chris", 19, 0, false);

    Predicate<Integer> isSenior =  level -> level >= 3;
    public static Predicate<Integer> isOld = age -> age >= 30;
    Predicate<Boolean> isGay = bool -> bool;
    Predicate <Integer> isNotTooOld = age -> age <= 40;
    Predicate<Integer> isYoungSenior = isSenior.and(isNotTooOld);

    public void runCombPred(Member user) {
        System.out.println(isYoungSenior.test(user.age));
    }

    //Method ref
    Function<String, Integer> getTextLength = String::length;


}
