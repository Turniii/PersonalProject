/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factorypattern.items;

import java.awt.Image;

/**
 *
 * @author Turni
 */
public interface IGadget {
    public void draw();
    public void move();
    void up();
    void down();
    public Boolean use();
}
