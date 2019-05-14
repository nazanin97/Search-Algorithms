package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("1-graph or 2-tree ?(enter number)");
        int input1  = scanner.nextInt();
        if (input1 != 1 && input1 != 2){
            System.out.println("Wrong input number!");
            return;
        }
        System.out.println("Enter your algorithm number:");
        System.out.println("1-BFS");
        System.out.println("2-DFS");
        System.out.println("3-Limited DFS");
        System.out.println("4-ID-DFS");
        System.out.println("5-Uniform Cost Search");
        System.out.println("6-Greedy Best-First Search");
        System.out.println("7-A*");
        int input2 = scanner.nextInt();
        if (!(input2 < 8 && input2 > 0)){
            System.out.println("Wrong input number!");
            return;
        }
        Algorithms a1 = new Algorithms(input1);
        a1.start(input2);
        System.out.println("Expanded Nodes:" + a1.getExpandedNodes());
        System.out.println("Observed Nodes:" + a1.getObservedNodes());
        System.out.println("Path to goal: " + a1.getResult());
        System.out.println("Cost to goal:" + a1.getCost(input2));
        System.out.println("Memory space usage:" + a1.getMaximumMemoryUsage());
    }
}
