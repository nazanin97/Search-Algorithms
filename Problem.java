package com.company;

import java.util.ArrayList;

public class Problem {

    private City initialState;
    private City goalState;

    public Problem(){

        setInitialState();
        setGoalState();
    }

    public void setInitialState(){
        City.getCity(2).father = null;
        initialState = City.getCity(2);
    }
    public void setGoalState(){
        goalState = City.getCity(12);
    }
    public City getInitialState(){
        return initialState;
    }

    /* action in each state
        city number is in range 0-19 */
    public ArrayList<Integer> action۳۳(City city){
        ArrayList<Integer> linkStates = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            if (City.getNeighbors(city.number)[i] != 0)
                linkStates.add(i);
        }
        return linkStates;
    }

    public boolean goalTest(City city){
        if (city.number == 12)
            return true;
        else
            return false;
    }
    public int stepCost(int node1, int node2){
        return City.getNeighbors(node1)[node2];
    }
    public int heuristic(int cityNum){
        int result = 0;
        switch (cityNum){
            case 0:
                result = 380;
                break;
            case 1:
                result = 374;
                break;
            case 2:
                result = 366;
                break;
            case 3:
                result = 329;
                break;
            case 4:
                result = 244;
                break;
            case 5:
                result = 241;
                break;
            case 6:
                result = 242;
                break;
            case 7:
                result = 253;
                break;
            case 8:
                result = 193;
                break;
            case 9:
                result = 160;
                break;
            case 10:
                result = 178;
                break;
            case 11:
                result = 98;
                break;
            case 12:
                result = 0;
                break;
            case 13:
                result = 77;
                break;
            case 14:
                result = 234;
                break;
            case 15:
                result = 226;
                break;
            case 16:
                result = 199;
                break;
            case 17:
                result = 80;
                break;
            case 18:
                result = 151;
                break;
            case 19:
                result = 161;
                break;
        }
        return result;
    }
}
