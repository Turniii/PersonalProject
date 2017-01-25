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
public class NoMoneyState implements IState{

    CoffeeMachine machine = null;
    
    public NoMoneyState(CoffeeMachine aThis) {
        this.machine = aThis;
    }

    @Override
    public void addMoney() {
        machine.setCurrentState(machine.getState("money"));
        System.out.println("Your Beloved Coffee Machine is happy");
    }

    @Override
    public void orderCoffee() {
        System.out.println("Your Beloved Coffee Machine can't give you free coffee");
    }

    @Override
    public void makeCoffee() {
        System.out.println("Your Beloved Coffee Machine can't give you free coffee");
    }

    @Override
    public void refill() {
        System.out.println("Your Beloved Coffee Machine don't need to be refilled, give me ");
    }
    
}
