package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class Algorithms {

    private int expandedNodes;
    private int observedNodes;
    private String result = "";
    private int cost;
    private ArrayList<Integer> memoryUsage;
    private int mode;
    private ArrayList<City> openList;   //f
    private ArrayList<City>closedList;  //e
    private Problem p1;
    private City c1;
    private City goal;

    int getExpandedNodes(){
        return expandedNodes;
    }
    int getObservedNodes(){
        return observedNodes;
    }
    int getCost(int input){
        if (goal == null)
            return 0;

        City hold = goal;
        if (input > 0 && input < 5){
            while (hold.father != null){
                cost++;
                hold = hold.father;
            }
        }
        if (input == 5){
            return goal.cost;
        }
        if (input == 6 || input == 7){
            while (hold.father != null){
                cost += p1.stepCost(hold.number, hold.father.number);
                hold = hold.father;
            }
        }
        return cost;
    }
    int getMaximumMemoryUsage(){
        int max = memoryUsage.get(0);
        for (int i = 1; i < memoryUsage.size(); i++) {
            if (memoryUsage.get(i) > max)
                max = memoryUsage.get(i);
        }
        return max;
    }
    String getResult(){

        if (goal == null)
            return "-";
        City hold = goal;
        while (hold != null){
            result = result + hold.number +  ", ";
            hold = hold.father;
        }
        return result;
    }
    private void bfs(){

            openList.add(p1.getInitialState());
            if (openList.size() == 0){
                System.out.println("Error");
                return;
            }
            observedNodes++;

            while (openList.size() != 0) {
                memoryUsage.add(openList.size());
                City chosenNode = openList.remove(0);
                if (p1.goalTest(chosenNode)){
                    goal = chosenNode;
                    return;
                }
                closedList.add(chosenNode);

                for (int i = 0; i < 20; i++) {
                    if (City.getNeighbors(chosenNode.number)[i] != 0) {
                        if (mode == 2) {
                            City city = new City(i);
                            city.father = chosenNode;
                            openList.add(city);
                            observedNodes++;
                        }
                        //if the mode is graph and open list doesn't have the node, add it
                        else if (!openList.contains(City.getCity(i)) && !(closedList.contains(City.getCity(i)))){
                            City.getCity(i).father = chosenNode;
                            openList.add(City.getCity(i));
                            observedNodes++;
                        }
                    }
                }
               expandedNodes++;
            }
    }
    private void dfs(){
        openList.add(p1.getInitialState());
        if (openList.size() == 0){
            System.out.println("Error");
            return;
        }
        dfs_running(openList.get(0), openList, closedList, Double.POSITIVE_INFINITY);
    }
    private void dfs_running(City chosenNode, ArrayList<City>openList, ArrayList<City>closedList, double limit){

        if (p1.goalTest(chosenNode)){
            goal = chosenNode;
            return;
        }
        memoryUsage.add(openList.size());
        observedNodes++;
        closedList.add(chosenNode);
        openList.remove(chosenNode);
        expandedNodes++;
        for (int i = 0; i < 20; i++) {
            if (City.getNeighbors(chosenNode.number)[i] != 0){

                int depth = 0;
                City temp = chosenNode;
                while (temp !=  null){
                    depth++;
                    temp = temp.father;
                }
                if (mode == 2 && depth <= limit){
                    City city = new City(i);
                    city.father = chosenNode;
                    openList.add(city);
                    dfs_running(city, openList, closedList, limit);
                }
                else if (!openList.contains(City.getCity(i)) && !closedList.contains(City.getCity(i)) && depth <= limit){

                    City.getCity(i).father = chosenNode;
                    openList.add(City.getCity(i));
                    dfs_running(City.getCity(i), openList, closedList, limit);
                }
            }
        }
    }
    private void limited_dfs(int limit){

        expandedNodes = 0;
        observedNodes = 0;
        openList.clear();
        memoryUsage.clear();
        closedList.clear();
        openList.add(p1.getInitialState());
        if (openList.size() == 0){
            System.out.println("Error");
            return;
        }
        dfs_running(openList.get(0), openList, closedList, limit);

    }
    private void id_dfs(){

        int depth = 1;
        do {
            limited_dfs(depth);
            depth++;
        } while (goal == null);

    }
    private void uniformCost_greedy_aStar(int status){

        p1.getInitialState().father = null;
        if (status == 3)
            p1.getInitialState().cost = p1.heuristic(2);

        openList.add(p1.getInitialState());
        if (openList.size() == 0){
            System.out.println("Error");
            return;
        }
        observedNodes++;

        while (openList.size() != 0){

            City chosenNode = openList.get(0);
            memoryUsage.add(openList.size());

            if (p1.goalTest(chosenNode)){
                goal = chosenNode;
                return;
            }

            closedList.add(chosenNode);
            openList.remove(0);

            for (int i = 0; i < 20; i++) {
                if (City.getNeighbors(chosenNode.number)[i] != 0) {
                    if (mode == 2) {
                        City city = new City(i);
                        city.father = chosenNode;

                        if (status == 1)
                            city.cost = City.getNeighbors(chosenNode.number)[i] + chosenNode.cost;
                        else if (status == 2)
                            city.cost = p1.heuristic(i);
                        else
                            city.cost = p1.heuristic(i) + City.getNeighbors(chosenNode.number)[i] + chosenNode.cost - p1.heuristic(chosenNode.number);

                        openList.add(city);
                        observedNodes++;
                    }
                    else if (!openList.contains(City.getCity(i)) && !(closedList.contains(City.getCity(i)))){

                        City.getCity(i).father = chosenNode;
                        if (status == 1)
                            City.getCity(i).cost = City.getNeighbors(chosenNode.number)[i] + chosenNode.cost;

                        else if (status == 2)
                            City.getCity(i).cost = p1.heuristic(i);

                        else
                            City.getCity(i).cost = p1.heuristic(i) + City.getNeighbors(chosenNode.number)[i] + chosenNode.cost - p1.heuristic(chosenNode.number);


                        openList.add(City.getCity(i));
                        observedNodes++;
                    }
                }
            }
            expandedNodes++;

            int min;
            int index = 0;
            min = openList.get(0).cost;
            for (int i = 1; i < openList.size(); i++) {
                if (openList.get(i).cost < min) {
                    min = openList.get(i).cost;
                    index = i;
                }
            }
            City temp = openList.get(0);
            openList.set(0, openList.get(index));
            openList.set(index, temp);
        }
    }

    void start(int num){
        switch (num){
            case 1:
                bfs();
                break;
            case 2:
                dfs();
                break;
            case 3:
                System.out.println("Enter your limit: ");
                Scanner scanner = new Scanner(System.in);
                limited_dfs(scanner.nextInt());
                break;
            case 4:
                id_dfs();
                break;
            case 5:
                uniformCost_greedy_aStar(1);
                break;
            case 6:
                uniformCost_greedy_aStar(2);
                break;
            case 7:
                uniformCost_greedy_aStar(3);
                break;
        }
    }

    Algorithms(int md){
        c1 = new City();
        c1.setMap();
        p1 = new Problem();
        openList = new ArrayList<>();
        closedList = new ArrayList<>();
        memoryUsage = new ArrayList<>();
        mode = md;
        goal = null;
    }
}
