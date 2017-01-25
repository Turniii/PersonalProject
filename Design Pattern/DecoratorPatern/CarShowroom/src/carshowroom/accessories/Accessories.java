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
public abstract class Accessories implements ICar
{
    protected ICar car;
    
    public Accessories(ICar carModel)
    {
        this.car = carModel;
    }
    
    @Override
    public int getPrice() {
        return car.getPrice();
    }

    @Override
    public String getDescription() 
    {
        return car.getDescription();
    }
    
    @Override
    public String getCarName() 
    {
        return car.getCarName();
    }
     
}
