package model;

import java.util.ArrayList;
import java.time.LocalDate;

/**
 * Manages all raffles in the system, acting as the entry point for UI operations
 */

public class RaffleManager{
    private ArrayList<Raffle> raffles;

    public RaffleManager(){
        this.raffles = new ArrayList<Raffle>();
    }

    public Raffle findRaffle(String name){
        for(int i = 0;i < raffles.size(); i++){
            Raffle currentRaffle = raffles.get(i);
            if (currentRaffle.getName().equals(name)){
                return currentRaffle;
            }
        }
        return null;
    }

    public boolean registerRaffle(String name, String description, double budget, LocalDate eventDate){

        Raffle existingRaffle = findRaffle(name);
        if(existingRaffle != null){
            return false;
        }
        Raffle newRaffle = new Raffle(name, description, budget, eventDate );
        raffles.add(newRaffle);

        return true;
    }

    public boolean modifyRaffle(String currentName, String newName, String newDescription, double newBudget, LocalDate newEventDate){

        Raffle lookRaffle = findRaffle(currentName);

        if(lookRaffle == null){
            return false;
        }
        if (!newName.equals(currentName)){

            Raffle duplicateRaffle = findRaffle(newName);
            if(duplicateRaffle != null){
                return false;
            }
        }
        lookRaffle.setName(newName);
        lookRaffle.setDescription(newDescription);
        lookRaffle.setSuggestedBudget(newBudget);
        lookRaffle.setEventDate(newEventDate);

        return true;
    }

    public boolean cloneRaffle(String originalName, String newName) {
    Raffle original = findRaffle(originalName);
    if (original == null) {
        return false;
    }
    Raffle duplicate = findRaffle(newName);
    if (duplicate != null) {
        return false;
    }
    Raffle clone = original.cloneRaffle(newName);
    raffles.add(clone);
    return true;
}
public boolean addParticipantToRaffle(String raffleName, String email, String name) {
    Raffle raffle = findRaffle(raffleName);
    if (raffle == null) {
        return false;
    }
    return raffle.addParticipant(email, name);
}

public boolean modifyParticipantInRaffle(String raffleName, String currentEmail, String newEmail, String newName) {
    Raffle raffle = findRaffle(raffleName);
    if (raffle == null) {
        return false;
    }
    return raffle.modifyParticipant(currentEmail, newEmail, newName);
}

public boolean removeParticipantFromRaffle(String raffleName, String email) {
    Raffle raffle = findRaffle(raffleName);
    if (raffle == null) {
        return false;
    }
    return raffle.removeParticipant(email);
}

public boolean addRestrictionToRaffle(String raffleName, String emailFrom, String emailTo) {
    Raffle raffle = findRaffle(raffleName);
    if (raffle == null) {
        return false;
    }
    return raffle.addRestriction(emailFrom, emailTo);
}

public boolean executeDraw(String raffleName) {
    Raffle raffle = findRaffle(raffleName);
    if (raffle == null) {
        return false;
    }
    return raffle.executeDraw();
}

public boolean annulRaffle(String raffleName) {
    Raffle raffle = findRaffle(raffleName);
    if (raffle == null) {
        return false;
    }
    raffle.annul();
    return true;
}

public String getRaffleStatusReport(String raffleName) {
    Raffle raffle = findRaffle(raffleName);
    if (raffle == null) {
        return "Raffle not found";
    }
    return raffle.getStatusReport();
}

public ArrayList<Raffle> getRaffles() {
    return raffles;
}
}