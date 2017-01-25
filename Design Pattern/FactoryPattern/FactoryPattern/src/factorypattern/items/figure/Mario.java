/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factorypattern.items.figure;

import factorypattern.StdDraw;
import factorypattern.items.IFigure;
import java.awt.Image;
import java.awt.event.KeyEvent;

/**
 *
 * @author Turni
 */
public class Mario implements IFigure{
public int posX = 60;
    public int posY = 80;
    public int jumpMax = 300;
    @Override
    public void up() {
       posY+=4;
    }

    @Override
    public void down() {
        posY-=4;
    }

    @Override
    public int getY() {
        return posY;
    }

    @Override
    public int getJumpMax() {
        return jumpMax;
    }
    
    
    

    @Override
    public void move() {
        if (StdDraw.isKeyPressed(KeyEvent.VK_LEFT)) {
                if (posX > 1) {
                    posX-=3;
                }
            }
            if (StdDraw.isKeyPressed(KeyEvent.VK_RIGHT)) {
                if (posX < 600) {
                    posX+=3;
                }
            }
    }
    

    @Override
    public void draw() {
        StdDraw.picture(posX, posY,"img/mario.png");
        //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
