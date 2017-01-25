/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mediatorpattern;

import mediatorpattern.frames.PresenterFrame;
import mediatorpattern.frames.AttendeeFrame;

/**
 *
 * @author Turni
 */

public class Attendee extends PresentationMember{
    
    private PresenterFrame pFrame;
    private AttendeeFrame aFrame;
    public Attendee(Mediator mediat) {
        super(mediat);
    }
    
     public void askQuestion(String question, AttendeeFrame aFrame, PresenterFrame pFrame)
    {      
        this.pFrame = pFrame;
        this.aFrame = aFrame;
        aFrame.setTextArea("You've asked the following question : "+question);
        mediator.sendQuestion(question,this, this.aFrame,this.pFrame);
    }
 
      public void receiveAnswer(String answer, AttendeeFrame aFrame){
        aFrame.setTextArea("Presenter sent your : "+ answer);
    }

    @Override
    public String toString() {
        return "Attendee{" + this.name +'}';
    }
      
    public void getNewImage(String image, AttendeeFrame attendeeFrame, String slide){
        attendeeFrame.setImage(image);
        attendeeFrame.setLabel(slide);
    }
}
