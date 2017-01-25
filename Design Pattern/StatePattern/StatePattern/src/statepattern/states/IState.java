/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statepattern.states;

/**
 *
 * @author Turni
 */
public interface IState {
    
    public void addMoney();
    public void orderCoffee();
    public void makeCoffee();
    public void refill();
    
    
}
