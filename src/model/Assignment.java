package model;

/**
 * This class represents an assignment between two participnts in a raffle, where one participant (giver) is assigned to gift another (receiver)
 */

public class Assignment{
    private Participant giver;
    private Participant receiver;

    public Assignment(Participant giver, Participant receiver){
        this.giver = giver;
        this.receiver = receiver;
    }

    public Participant getGiver(){
        return giver;
    }
    public Participant getReceiver(){
        return receiver;
    }
}