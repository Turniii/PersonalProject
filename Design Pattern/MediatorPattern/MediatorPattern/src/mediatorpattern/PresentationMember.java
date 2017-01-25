/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mediatorpattern;

import mediatorpattern.frames.PresenterFrame;

/**
 *
 * @author Turni
 */
public abstract  class PresentationMember {
    
    protected Mediator mediator;
    public String name;
    
   private PresenterFrame pFrame;
    public PresentationMember(Mediator mediat){
        this.mediator = mediat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
   

}
