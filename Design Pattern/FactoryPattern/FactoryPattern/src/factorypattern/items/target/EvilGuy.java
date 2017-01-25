/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factorypattern.items.target;

import factorypattern.StdDraw;
import java.awt.Image;
import factorypattern.items.ITarget;

/**
 *
 * @author Turni
 */
public class EvilGuy implements ITarget{

    @Override
    public void draw() {
           StdDraw.picture(600, 75, "img/evilGuy.png");
    }
    
}
