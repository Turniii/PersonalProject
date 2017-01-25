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
public class EmptyState implements IState {
       
    CoffeeMachine machine = null;

    
    public EmptyState(CoffeeMachine aThis) {
        this.machine = aThis;
    }
    
    
    
    @Override
    public void addMoney() {
        System.out.println("Your Beloved Coffee Machine is empty so don't give me money");
    }

    @Override
    public void orderCoffee() {
        System.out.println("Your Beloved Coffee Machine is empty so you can't order coffee");
    }

    @Override
    public void makeCoffee() {
        System.out.println("Your Beloved Coffee Machine is empty so I can't make you coffee");
    }

    @Override
    public void refill() {
        System.out.println("Your Beloved Coffee Machine as been refilled");
        machine.setCurrentState(machine.getState("noMoney"));
        machine.setCapacity(4);
    }
    
}
