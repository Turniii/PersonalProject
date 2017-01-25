/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carshowroom.accessories;

import carshowroom.ICar;

/**
 *
 * @author adhraa
 */
public class Bullbar extends Accessories
{
    private int bullbarPrice =150;
    public Bullbar(ICar carModel) 
    {
        super(carModel);
    }
    
    @Override
    public int getPrice() 
    {
        return car.getPrice() + bullbarPrice;
    }

    @Override
    public String getDescription() 
    {
        return car.getDescription() + "Bullbar: $"+ bullbarPrice+"<br>";
    }

    @Override
    public String getCarName() 
    {
        return car.getCarName();
    }
}
