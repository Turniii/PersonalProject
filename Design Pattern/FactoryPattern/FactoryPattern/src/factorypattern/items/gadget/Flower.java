/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factorypattern.items.gadget;

import factorypattern.StdDraw;
import factorypattern.items.IGadget;
import java.awt.Image;
import java.awt.event.KeyEvent;

/**
 *
 * @author Turni
 */
public class Flower implements IGadget{
    private int posX = 30;
    private int posY = 90;
    @Override
    public void draw() {
        StdDraw.picture(posX,posY, "img/flower.png");
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
    public void up() {
       posY+=4;
    }

    @Override
    public void down() {
        posY-=4;
    }

   
    @Override
    public Boolean use() {
        int departX = posX;
        int avance = 4;
        int tempPosX = departX;
        Boolean inUse = true;
        while (inUse){
            tempPosX = departX + avance;
            StdDraw.setPenColor(251, 49, 0);
            StdDraw.filledCircle(tempPosX, posY+30, 3);
            StdDraw.show();
            StdDraw.pause(3);
            avance++;
            if (avance >=160){
                inUse = false;
                StdDraw.picture(tempPosX, posY, "img/fire.png");
                StdDraw.show();
            StdDraw.pause(300);
            }
            else if (tempPosX >= 500){
                inUse = false;
                StdDraw.picture(tempPosX, posY, "img/fire.png");
                StdDraw.show();
                StdDraw.pause(500);
                return true;
            }
        }
         return false;       
    }
    
    
}
