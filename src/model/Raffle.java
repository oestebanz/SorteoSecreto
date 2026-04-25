package model;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * This class represents the raffle itself, including its participants, restrictions, and assigments
 */

public class Raffle{

private String name;
private String description;
private double suggestedBudget;
private LocalDate eventDate;
private RaffleStatus status;
private ArrayList<Participant> participants;
private ArrayList<Restriction> restrictions;
private ArrayList<Assignment> assignments;



public Raffle(String name, String description, double suggestedBudget, LocalDate eventDate){

    this.name = name;
    this.description = description;
    this.suggestedBudget = suggestedBudget;
    this.eventDate = eventDate;
    this.participants = new ArrayList<Participant>();
    this.restrictions = new ArrayList<Restriction>();
    this.assignments = new ArrayList<Assignment>();
    this.status = RaffleStatus.CREATED;
}

public String getName(){
    return name;
}

public String getDescription(){
    return description;
}


public double getSuggestedBudget(){
    return suggestedBudget;
}

public LocalDate getEventDate(){
    return eventDate;
}

public RaffleStatus getStatus(){
    return status;
}

public ArrayList<Participant> getParticipants(){
    return participants;
}

public ArrayList<Restriction> getRestrictions(){
    return restrictions;
}

public ArrayList<Assignment> getAssignments(){
    return assignments;
}
public void setName(String name){
    this.name = name;
}

public void setDescription(String description){
    this.description = description;
}

public void setSuggestedBudget(double suggestedBudget){
    this.suggestedBudget = suggestedBudget;
}

public void setEventDate(LocalDate eventDate){
    this.eventDate = eventDate;
}

public Participant findParticipant(String email){

    for (int i = 0; i < participants.size(); i++){

        Participant current = participants.get(i);
        if (current.getEmail().equals(email)){
            return current;
        }
    }
    return null;
}

public boolean addParticipant(String email, String name){


    Participant existing = findParticipant(email);
    if (existing != null){
        return false;
    }
    

    Participant newParticipant = new Participant(email, name);
    
    participants.add(newParticipant);

    return true;
}

public boolean removeParticipant(String email){

    

    
}
}