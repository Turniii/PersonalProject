/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factorypattern.Factories;

import factorypattern.IFactory;
import factorypattern.StdDraw;
import factorypattern.items.IFemale;
import factorypattern.items.IFigure;
import factorypattern.items.IGadget;
import factorypattern.items.female.Peach;

import factorypattern.items.figure.Mario;
import factorypattern.items.gadget.Flower;
import factorypattern.items.target.Bowser;
import factorypattern.items.ITarget;


/**
 *
 * @author Turni
 */
public class MarioFactory implements IFactory {
    public String name = "Mario Game";
    
    
    @Override
    public IFigure createFigure() {
        return new Mario();
    }

    @Override
    public String getName() {
        return "Mario Game";
    }

    @Override
    public String getBackground() {
        return new String("img/marioBack.png");
    }

    @Override
    public IFemale createFemale() {
        return new Peach();
    }

    @Override
    public IGadget createGadget() {
        return new Flower();
    }

    @Override
    public ITarget createHouse() {
        return new Bowser();
    }
    @Override
    public void drawBackground(){
        StdDraw.picture(300, 200, "img/marioBack.png");
    }

    @Override
    public void drawEnd() {
        StdDraw.picture(300, 200, "img/marioWin.png");
    }
    
}
