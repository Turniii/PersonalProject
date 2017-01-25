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
public class DispensingState implements IState{

    CoffeeMachine machine = null;
    
    public DispensingState(CoffeeMachine aThis) {
        this.machine = aThis;
    }

    @Override
    public void addMoney() {
        System.out.println("Your Beloved Coffee Machine is already making coffee");
    }

    @Override
    public void orderCoffee() {
        System.out.println("Your Beloved Coffee Machine is already making coffee");
    }

    @Override
    public void makeCoffee() {
        int capacity = machine.getCapacity();
        capacity--;
        if (capacity == 0){
            machine.setCurrentState(machine.getState("empty"));
            machine.setCapacity(capacity);
            System.out.println("Your Beloved Coffee Machine is now empty");
        }
        else {
            machine.setCurrentState(machine.getState("noMoney"));
            machine.setCapacity(capacity);
            System.out.println("Your Beloved Coffee Machine is ready for the next coffee");
        }
        
    }

    @Override
    public void refill() {
        System.out.println("Your Beloved Coffee Machine is already making coffee");
    }
    
}
