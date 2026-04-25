package model;

/**
 * This class represents a person participating within the raffle.
 */

public class Participant{

    private String email;
    private String name;

    public Participant(String email, String name){
        this.email = email;
        this.name = name;
    }

    public String getEmail(){
        return email;

    }
    public String getName(){
        return name;
    }

    public void setEmail(String email){
        this.email = email;
    }
    public void setName(String name){
        this.name = name;
    }
    /**
     * This method compares two participants by their emails.
     */
        @Override
        public boolean equals(Object obj) {
        Participant other = (Participant) obj;
        return this.email.equals(other.email);
    }
}

