package org.example.Day11;

import java.util.function.Predicate;

public class Day11 {
    sealed interface Result<s, f> permits Success, Failure {}
    record Success<s, f>(s value) implements Result<s, f> {}
    record Failure<s, f>(f value) implements Result<s, f> {}

    static Predicate<Integer> isEvenOrNot = i -> i%2 == 0;

    static Result<String, String> res(int i) {
        if (isEvenOrNot.test(i)) {
            return new Success<>(i + " not even");
        } else {
            return new Failure<>(i + " not even");
        }
    }

    public static void main(String[] args) {
        Result<String, String> ret = res(13);

        switch (ret) {
            case Success<String, String>(String value) -> System.out.println(value);
            case Failure<String, String>(String value) -> System.out.println(value);
        }

    }


}

