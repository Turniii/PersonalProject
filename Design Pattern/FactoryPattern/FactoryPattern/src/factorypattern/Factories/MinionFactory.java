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
import factorypattern.items.female.MinionGirl;
import factorypattern.items.figure.Minion;
import factorypattern.items.gadget.FreezeGun;
import factorypattern.items.target.EvilGuy;
import factorypattern.items.ITarget;

/**
 *
 * @author Turni
 */
public class MinionFactory implements IFactory{

    public String name = "Minion Game";
    
    
    @Override
    public IFigure createFigure() {
        return new Minion();
    }

    @Override
    public String getName() {
        return "Minion Game";
    }

    @Override
    public String getBackground() {
        return new String("img/minionBack.jpg");
    }

    @Override
    public IFemale createFemale() {
        return new MinionGirl();
    }

    @Override
    public IGadget createGadget() {
        return new FreezeGun();
    }

    @Override
    public ITarget createHouse() {
        return new EvilGuy();
    }
    @Override
    public void drawBackground(){
        StdDraw.picture(300, 200, "img/minionBack.jpg");
    }

    @Override
    public void drawEnd() {
        StdDraw.picture(300, 200, "img/minionWin.jpg");
    }
    
    
    
}
