/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statepattern.states;

import statepattern.CoffeeMachine;

/**
 *
 * @author Turni
 */
public class MoneyState implements IState{

    CoffeeMachine machine = null;
    
    public MoneyState(CoffeeMachine aThis) {
        this.machine = aThis;
    }

    @Override
    public void addMoney() {
        System.out.println("Your Beloved Coffee Machine don't need more money");
    }

    @Override
    public void orderCoffee() {
        machine.setCurrentState(machine.getState("dispensingState"));
        System.out.println("Your Beloved Coffee Machine start to do your coffee");
    }

    @Override
    public void makeCoffee() {
        System.out.println("Press the button button");
        
    }

    @Override
    public void refill() {
        System.out.println("Your Beloved Coffee Machine don't need to be refilled, give me ");
    }
    
}
