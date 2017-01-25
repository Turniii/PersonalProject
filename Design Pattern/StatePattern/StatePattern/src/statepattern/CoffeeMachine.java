/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statepattern;

import statepattern.states.DispensingState;
import statepattern.states.EmptyState;
import statepattern.states.IState;
import statepattern.states.MoneyState;
import statepattern.states.NoMoneyState;

/**
 *
 * @author Turni
 */
public class CoffeeMachine {
    IState dispensingState = new DispensingState(this);
    IState emptyState = new EmptyState(this);
    IState moneyState =new MoneyState(this);
    IState noMoneyState = new NoMoneyState(this);
    IState currentState = null;
    int capacity = 0;
    long previousClicTime = 0;
    boolean clicked = false;
    public CoffeeMachine() {
        this.currentState = emptyState;
        this.capacity = 0;
    }

    public void clic(long clicTime){
        if (StdDraw.mousePressed()){
            if (clicTime > previousClicTime + 200){
                if(StdDraw.mouseX() >= 222 && StdDraw.mouseX() <= 229 ){
                    if (StdDraw.mouseY() >= 130 && StdDraw.mouseY() <=155){
                        orderCoffee();
                    }

                }
                else if(StdDraw.mouseX() >= 403 && StdDraw.mouseX() <= 414 ){
                    if (StdDraw.mouseY() >= 130 && StdDraw.mouseY() <=163){
                        refill();
                    }

                }
                else if(StdDraw.mouseX() >= 290 && StdDraw.mouseX() <= 350 ){
                    if (StdDraw.mouseY() >= 260 && StdDraw.mouseY() <=385){
                        addMoney();
                    }

                }
                previousClicTime = clicTime;
            }
           
            
        }
    }
    public IState getCurrentState() {
        return currentState;
    }
    
    public IState getState(String state){
        switch(state){
            case "empty":
                return emptyState;
            case "money":
                return moneyState;
            case "noMoney":
                return noMoneyState;
            case "dispensingState":
                return dispensingState;
            default:
                return null;
        }
    }
    
    public void setCurrentState(IState currentState) {
        this.currentState = currentState;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    
    
    
    public void orderCoffee(){
        this.currentState.orderCoffee();
    }
    
    public void refill(){
        this.currentState.refill();
    }
    
    public void addMoney(){
        this.currentState.addMoney();
    }
    public void makeCoffee(){
        this.currentState.makeCoffee();
    }
    
    
}
