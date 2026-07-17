package org.example;

import org.example.day08.Day8;

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

        Day8 day8runner = new Day8();

        System.out.println("combine andThen: " + day8runner.andThenTest.apply(5));
        System.out.println("combine compose: " + day8runner.composeTest.apply(5));
    }
}