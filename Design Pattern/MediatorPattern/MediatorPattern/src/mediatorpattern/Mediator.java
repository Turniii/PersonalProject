/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mediatorpattern;

import java.util.ArrayList;
import java.util.List;
import mediatorpattern.frames.PresenterFrame;
import mediatorpattern.frames.AttendeeFrame;

/**
 *
 * @author Turni
 */
public class Mediator {
    
    public Presenter presenter;
    public List<Attendee> attendees;
    
     private PresenterFrame pFrame;
    private AttendeeFrame aFrame;
    
    
    public Mediator (Presenter presenter){
        this.presenter = presenter;
        this.attendees = new ArrayList<Attendee>();
    }
    
    /*public void addAttendee(Attendee newAttendee){
        attendees.add(newAttendee);
    }*/
    
    public void sendAnswer( String answer,Attendee attendee,AttendeeFrame aFrame){
       attendee.receiveAnswer(answer, aFrame);
    }
    
    public void sendQuestion(String question, Attendee attendee, AttendeeFrame aframe, PresenterFrame pFrame){ 
        presenter.receiveQuestion(question, attendee,  pFrame);
    }
    
    public List<Attendee> getattendees()
    {
     return attendees;
    }
    
     public void setattendees(ArrayList<Attendee> attendees)
    {
     this.attendees = attendees;
    }
    
    public void sendImage(String image, AttendeeFrame attendeeFrame, String slide){
        attendeeFrame.attendee.getNewImage(image, attendeeFrame, slide);
    }
}
