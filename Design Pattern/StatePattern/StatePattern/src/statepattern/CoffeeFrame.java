/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statepattern;

import java.awt.event.MouseEvent;

/**
 *
 * @author Turni
 */
public class CoffeeFrame {
    
    public static void main(String[] args) {
        CoffeeMachine coffeeMachine = new CoffeeMachine();
        StdDraw.setCanvasSize(600, 400);
        StdDraw.setXscale(0, 600);
        StdDraw.setYscale(0, 400);
        StdDraw.enableDoubleBuffering();
        StdDraw.picture(300, 200, "img/coffeeMachine.png");
        long startTime = 0;
       
      int i = 0;
      boolean stop = false;
        while(!stop){
            if (coffeeMachine.getCurrentState() == coffeeMachine.getState("empty")){
                StdDraw.picture(300, 200, "img/backgroundEmpty.png");
            }
            else if (coffeeMachine.getCurrentState() == coffeeMachine.getState("money")){
                StdDraw.picture(300, 200, "img/backgroundMoney.png");
            }
            else if (coffeeMachine.getCurrentState() == coffeeMachine.getState("noMoney")){
                StdDraw.picture(300, 200, "img/backgroundnoMoney.png");
            }
            else {
                StdDraw.picture(300, 200, "img/background.png");
            }
            
            
            switch (coffeeMachine.getCapacity()){
                case 0:
                    StdDraw.picture(300, 150, "img/coffeeMachine.png");
                    break;
                case 1:    
                    StdDraw.picture(300, 150, "img/coffeeMachine1.png");
                    break;
                case 2:
                    StdDraw.picture(300, 150, "img/coffeeMachine2.png");
                    break;
                case 3:
                    StdDraw.picture(300, 150, "img/coffeeMachine3.png");
                    break;
                case 4:
                    StdDraw.picture(300, 150, "img/coffeeMachine4.png");
                    break;  
                default :
                    StdDraw.picture(300, 150, "img/coffeeMachine.png");
                    break;
                    
            }
            
            if (coffeeMachine.currentState == coffeeMachine.getState("dispensingState")){
                StdDraw.picture(305, 136, "img/tasseMake.png");
                StdDraw.show();
                StdDraw.pause(2000);
                coffeeMachine.makeCoffee();
        }
            StdDraw.picture(580, 380, "img/kill.png");
        startTime = System.currentTimeMillis();
        coffeeMachine.clic(startTime);
        if (StdDraw.mousePressed()){

            if(StdDraw.mouseX() >= 560){
                if (StdDraw.mouseY() >= 360){
                    System.exit(0);
                }
            }
        }
        StdDraw.show();
        StdDraw.pause(20);
        StdDraw.clear();
        }
    }
    

}
