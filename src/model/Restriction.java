package model;

/**
 * This class represents a directional restriction.
 */
public class Restriction{
    private Participant from;
    private Participant to;

    public Restriction(Participant from, Participant to){
        this.from = from;
        this.to = to;
    }

    public  Participant getFrom(){
        return from;
    }
    public Participant getTo(){
        return to;
    }
}