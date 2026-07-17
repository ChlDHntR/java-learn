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

}
