package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.print("Hello World!");

        //TIP Press <shortcut actionId="Debug"/> to start debugging your code. We have set one <icon src="AllIcons.Debugger.Db_set_breakpoint"/> breakpoint
        // for you, but you can always add more by pressing <shortcut actionId="ToggleLineBreakpoint"/>.

        System.out.println("Testing ends here");
        System.out.println("-------------------------");
        System.out.println();

        //Learning starts from here!
        //Have fun!

        Box<Integer> newBox = new Box<>();

        newBox.setValue(2);
        System.out.println("Box value: " + newBox.unboxValue());

        Pair<Boolean, String> newPair = new Pair<>();
        newPair.setValue1(true);
        newPair.setValue2("this is value #2");

        newPair.getValues();

        ArrayList<Integer> numList = new ArrayList<>();

        for (int i = 1; i <= 6; i++) {
            int r = new Random().nextInt(101);
            numList.add(i*i + r);
        }

        System.out.println(max(numList));

        benchmarkTest test = new benchmarkTest();
        test.runBenchMark();

    }

    static <T extends Comparable<T>> T max(List<T> list) {
        T max = list.getFirst();
        ListIterator<T> lit = list.listIterator();
        T nextNode;

        while (lit.hasNext()) {
            nextNode = lit.next();
            max = nextNode.compareTo(max) > 0 ? nextNode : max;
        }

        return max;
    }

    static class Box<T> {
        private T value;

        private void setValue(T value) {
            this.value = value;
        }

        private T unboxValue() {
            return value;
        }
    }

    static class Pair<K,V> {
        private K value1;
        private V value2;

        private void setValue1(K value1) {
            this.value1 = value1;
        }

        private void setValue2(V value2) {
            this.value2 = value2;
        }

        private void getValues() {
            System.out.println("value 1: " + value1);
            System.out.println("value 2: " + value2);
        }
    }
}