/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factorypattern;

import factorypattern.items.IFemale;
import factorypattern.items.IFigure;
import factorypattern.items.IGadget;
import factorypattern.items.ITarget;

/**
 *
 * @author Turni
 */
public interface IFactory {
    String getName();
    IFigure createFigure();
    IFemale createFemale();
    IGadget createGadget();
    ITarget createHouse();
    String getBackground();
    void drawBackground();
    void drawEnd();
}
