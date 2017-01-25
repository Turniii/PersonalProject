/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factorypattern.items.female;

import factorypattern.StdDraw;
import factorypattern.items.IFemale;
import java.awt.Image;

/**
 *
 * @author Turni
 */
public class Peach implements  IFemale{

    @Override
    public void draw() {
       StdDraw.picture(30, 230,"img/peach.png");
    }
    
}
