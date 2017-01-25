/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factorypattern;

import factorypattern.Factories.MarioFactory;
import factorypattern.Factories.MinionFactory;
import factorypattern.items.IFemale;
import factorypattern.items.IFigure;
import factorypattern.items.IGadget;
import factorypattern.items.female.MinionGirl;
import factorypattern.items.figure.Minion;
import factorypattern.items.gadget.FreezeGun;
import factorypattern.items.target.EvilGuy;
import java.awt.Font;
import java.awt.event.KeyEvent;
import factorypattern.items.ITarget;

/**
 *
 * @author Turni
 */
public class GenericGame {
    
    
    
    public static void main(String[] args) {
        int ground = 80;
        State state = State.MENU;
        StdDraw.setCanvasSize(600, 400);
        StdDraw.setXscale(0, 600);
        StdDraw.setYscale(0, 400);
        StdDraw.enableDoubleBuffering();
        IFactory factory = new MinionFactory();
        IFigure figure = new Minion();
        IFemale female = new MinionGirl();
        IGadget gadget = new FreezeGun();
        ITarget house = new EvilGuy();
        Boolean jump = false;
        Boolean up = true;
        Boolean endGame = false;
        
        while (!StdDraw.isKeyPressed(KeyEvent.VK_ESCAPE)){
            if (null != state)switch (state) {
                case MENU:
                    state = playMenu();
                    break;
                case INITJEUMINION:
                    factory = new MinionFactory();
                    figure = factory.createFigure();
                    female = factory.createFemale();
                    gadget = factory.createGadget();
                    house = factory.createHouse();
                    state = State.JEU;
                    break;
                case INITJEUMARIO: 
                    factory = new MarioFactory();
                    figure = factory.createFigure();
                    female = factory.createFemale();
                    gadget = factory.createGadget();
                    house = factory.createHouse();
                    state = State.JEU;
                    break;
                case JEU:
                    factory.drawBackground();
                    StdDraw.setFont(new Font("Arial", Font.BOLD, 20));
                    StdDraw.text(300, 370, "move : arrows    jump : up    use gadget : space");
                    StdDraw.setFont(new Font("Arial", Font.BOLD, 15));
                    StdDraw.text(300, 330, "HIT THE NASTY ONE !");
                    female.draw();
                    house.draw();
                    figure.draw();
                    gadget.draw();
                    
                    if (jump == true){
                        if (up == true){
                            if (figure.getY() <= figure.getJumpMax()){
                                gadget.up();
                                figure.up();
                                figure.move();
                                gadget.move();
                                
                            }
                            else up = false;
                        }
                        else {
                            if (figure.getY() >= ground){
                                gadget.down();
                                figure.down();
                                figure.move();
                                gadget.move();
                                
                            }
                            else {
                                up = true;
                                jump = false;
                            }
                        }
                    }
                    else {
                        figure.move();
                        gadget.move();
                        if (StdDraw.isKeyPressed(KeyEvent.VK_UP)){
                            jump = true;
                        }
                        if (StdDraw.isKeyPressed(KeyEvent.VK_SPACE)){
                            endGame = gadget.use();
                            if (endGame){
                                state = State.END;
                            }
                        }
                    }   break;
                case END :
                    state = playEnd(factory);
                    break;
                default:
                    break;
            }
           
            
            StdDraw.show();
            if (jump){
               StdDraw.pause(10); 
            }
            else StdDraw.pause(20); 
            
            StdDraw.clear();
        }

        System.exit(0);
        
    }
    private static State playEnd(IFactory fact){
        fact.drawEnd();
        StdDraw.setFont(new Font("Arial", Font.BOLD, 20));
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.text(150, 275, "MENU");
        
        if (StdDraw.mousePressed()){
            if(StdDraw.mouseX() >= 120 && StdDraw.mouseX() <= 180 ){
                if (StdDraw.mouseY() >= 265 && StdDraw.mouseY() <=285){
                    return State.MENU;
                }
                
            }
        }
        return State.END;
    }
    private static State playMenu(){
        StdDraw.picture(300, 200, "img/menuBack.jpg");
        StdDraw.setFont(new Font("Arial", Font.BOLD, 20));
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(300, 300, "Choose your game !");
        StdDraw.picture(200, 150, "img/minionSprite.png");
        StdDraw.picture(400, 150, "img/mario.png");
        StdDraw.text(300, 30, "EXIT");
        if (StdDraw.mousePressed()){
            if(StdDraw.mouseY() >= 70 && StdDraw.mouseY() <= 230 ){
                if (StdDraw.mouseX() >= 158 && StdDraw.mouseX() <=242){
                    return State.INITJEUMINION;
                }
                else if (StdDraw.mouseX() >= 358 && StdDraw.mouseX() <=442){
                    return State.INITJEUMARIO;
                }
            }
            else if (StdDraw.mouseY() >= 10 && StdDraw.mouseY() <= 50 ){
                if (StdDraw.mouseX() >= 270 && StdDraw.mouseX() <=330){
                    System.exit(0);
                }
                
            }
        }
        return State.MENU;
    }
}
