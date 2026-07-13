package org.example;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class benchmarkTest {
    private ArrayList<Integer> arrayL = new ArrayList<>();
    private LinkedList<Integer> linkedL = new LinkedList<>();
    private long arrayL_headInsert;
    private long arrayL_tailInsert;
    private long arrayL_midInsert;
    private long linkedL_headInsert;
    private long linkedL_tailInsert;
    private long linkedL_midInsert;
    private long start;

    public void runBenchMark() {
        //head insert
        System.out.println("run head insert benchmark");
        System.out.println(runInsert(arrayL_headInsert, linkedL_headInsert, () -> arrayL.add(0,1), () -> linkedL.add(0,1)));
        clearList();

        //Middle insert
        System.out.println("run middle insert benchmark");
        System.out.println(runInsert(arrayL_midInsert, linkedL_midInsert, () -> arrayL.add(getMidIndex(arrayL), 1), () -> linkedL.add(getMidIndex(linkedL), 1)));
        clearList();
    }

    private void clearList() {
        arrayL = new ArrayList<>();
        linkedL = new LinkedList<>();
    }

    private ArrayList<Number> runInsert(long arrayL_insert, long linkedL_insert, Runnable callback1, Runnable callback2) {
        ArrayList<Number> result = new ArrayList<Number>();
        //ArrayList
        start = System.nanoTime();
        for (int i = 1; i <= 100000; i++) {
            callback1.run();
        }
        arrayL_insert = System.nanoTime() - start;
        //LinkedList
        start = System.nanoTime();
        for (int i = 1; i <= 100000; i++) {
            callback2.run();
        }
        linkedL_insert = System.nanoTime() - start;
        result.add(arrayL_insert);
        result.add(linkedL_insert);
        return result;
    }

    private <T> int getMidIndex(List<T> list) {
        return (int) ((list.size() - 1) / 2);
    }

}
