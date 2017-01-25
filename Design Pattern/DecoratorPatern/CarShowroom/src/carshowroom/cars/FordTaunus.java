package carshowroom.cars;

import carshowroom.ICar;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author adhraa
 */
public class FordTaunus implements ICar
{

    @Override
    public int getPrice()
    {
        return 27220;
    }

    @Override
    public String getDescription() {
        return "";
    }
    
     @Override
    public String getCarName() 
    {
        return "Ford Taunus";
    }
    
}
