package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;

/**
 * This class represents the raffle itself, including its participants,
 * restrictions, and assigments
 */
public class Raffle {

    private String name;
    private String description;
    private double suggestedBudget;
    private LocalDate eventDate;
    private RaffleStatus status;
    private ArrayList<Participant> participants;
    private ArrayList<Restriction> restrictions;
    private ArrayList<Assignment> assignments;

    public Raffle(String name, String description, double suggestedBudget, LocalDate eventDate) {

        this.name = name;
        this.description = description;
        this.suggestedBudget = suggestedBudget;
        this.eventDate = eventDate;
        this.participants = new ArrayList<Participant>();
        this.restrictions = new ArrayList<Restriction>();
        this.assignments = new ArrayList<Assignment>();
        this.status = RaffleStatus.CREATED;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getSuggestedBudget() {
        return suggestedBudget;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public RaffleStatus getStatus() {
        return status;
    }

    public ArrayList<Participant> getParticipants() {
        return participants;
    }

    public ArrayList<Restriction> getRestrictions() {
        return restrictions;
    }

    public ArrayList<Assignment> getAssignments() {
        return assignments;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSuggestedBudget(double suggestedBudget) {
        this.suggestedBudget = suggestedBudget;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public Participant findParticipant(String email) {

        for (int i = 0; i < participants.size(); i++) {

            Participant current = participants.get(i);
            if (current.getEmail().equals(email)) {
                return current;
            }
        }
        return null;
    }

    public boolean addParticipant(String email, String name) {

        Participant existing = findParticipant(email);
        if (existing != null) {
            return false;
        }

        Participant newParticipant = new Participant(email, name);

        participants.add(newParticipant);

        return true;
    }

    public boolean removeParticipant(String email) {

        Participant removed = findParticipant(email);
        if (removed == null) {
            return false;
        }
        participants.remove(removed);
        return true;

    }

    public boolean modifyParticipant(String currentEmail, String newEmail, String newName) {

        Participant lookParticipant = findParticipant(currentEmail);

        if (lookParticipant == null) {
            return false;
        }
        if (!newEmail.equals(currentEmail)) {

            Participant duplicate = findParticipant(newEmail);
            if (duplicate != null) {
                return false;
            }
        }
        lookParticipant.setEmail(newEmail);
        lookParticipant.setName(newName);
        return true;
    }

    public boolean addRestriction(String emailFrom, String emailTo) {

        Participant from = findParticipant(emailFrom);
        Participant to = findParticipant(emailTo);
        if (from == null || to == null) {
            return false;

        }
        if (emailFrom.equals(emailTo)) {
            return false;
        }
        Restriction newRestriction = new Restriction(from, to);
        restrictions.add(newRestriction);
        return true;

    }

    public void annul() {
        this.status = RaffleStatus.ANNULLED;
    }

    public Raffle cloneRaffle(String newName) {
        Raffle clone = new Raffle(newName, this.description, this.suggestedBudget, this.eventDate);
        for (int i = 0; i < participants.size(); i++) {
            Participant current = participants.get(i);

            clone.addParticipant(current.getEmail(), current.getName());
        }
        return clone;
    }

    public String getStatusReport() {

        String report = "Name: " + getName();
        report += "\nDescription: " + getDescription();
        report += "\nSuggested Budget: " + getSuggestedBudget();
        report += "\nEvent Date: " + getEventDate();
        report += "\nStatus: " + getStatus();

        for (int i = 0; i < participants.size(); i++) {
            report += "\n- Name: " + participants.get(i).getName();
            report += "\n- Email: " + participants.get(i).getEmail();

        }
        return report;
    }

    public boolean executeDraw() {
        if (this.status != RaffleStatus.CREATED) {
            return false;
        }
        if (participants.size() < 2) {
            return false;
        }

        Random random = new Random();

        for (int j = 0; j < 1000; j++) {
            ArrayList<Participant> shuffled = new ArrayList<Participant>(participants);
            Collections.shuffle(shuffled, random);
            ArrayList<Assignment> tempAssignments = new ArrayList<Assignment>();

            for (int i = 0; i < shuffled.size(); i++) {

                Participant giver = shuffled.get(i);
                Participant receiver = shuffled.get((i + 1) % shuffled.size());
                Assignment newAssignment = new Assignment(giver, receiver);
                tempAssignments.add(newAssignment);

            }

            if (isValidAssignmentList(tempAssignments)) {
                this.assignments = tempAssignments;
                this.status = RaffleStatus.DRAWN;

                return true;
            }
        }
        return false;
    }

    private boolean isValidAssignmentList(ArrayList<Assignment> assignmentList) {
        for (int i = 0; i < assignmentList.size(); i++){
            Assignment currentAssignment = assignmentList.get(i);

            for(int j = 0; j < restrictions.size(); j++){
                Restriction currentRestriction = restrictions.get(j);
                if (currentAssignment.getGiver().equals(currentRestriction.getFrom()) && currentAssignment.getReceiver().equals(currentRestriction.getTo())){
                    return false;
                }
            }
        }
        return true;
}

}
