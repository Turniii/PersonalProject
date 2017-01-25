/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carshowroom;

import carshowroom.accessories.AirConditioner;
import carshowroom.accessories.Bullbar;
import carshowroom.accessories.SnowTires;
import carshowroom.cars.FordTaunus;
import carshowroom.cars.VolkswagenBeetle;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

/**
 *
 * @author Turni
 */
public class Utils {
       
    private ICar car;
    private JLabel labelName, labelDesc, labelPrice;

    public Utils(ICar car, JLabel labelName, JLabel labelDesc, JLabel labelPrice) {
        this.car = car;
        this.labelName = labelName;
        this.labelDesc = labelDesc;
        this.labelPrice = labelPrice;
    }
    
    
    
    public ICar setNewCar(JRadioButton jRadioButton1, JRadioButton jRadioButton2, JCheckBox jCheckBox1, JCheckBox jCheckBox2, JCheckBox jCheckBox3 ){
        car = null;
        if (jRadioButton1.isSelected()){
            car = new FordTaunus();
        }
        else if(jRadioButton2.isSelected()){
            car = new VolkswagenBeetle();
        }
        if (car != null){
            if (jCheckBox1.isSelected()){
                car = new AirConditioner(car);
            }
            if (jCheckBox2.isSelected()){
                car = new Bullbar(car);
            }
            if (jCheckBox3.isSelected()){
                car = new SnowTires(car);
            }
        }
        return car;
    }
    public void displayInfos(){
        if (car != null){
            labelName.setText(car.getCarName());
            labelDesc.setText(car.getDescription());
            labelPrice.setText(Integer.toString(car.getPrice()));
        }
        else {
            labelName.setText("");
            labelDesc.setText("");
            labelPrice.setText("");
        }

    }
}
