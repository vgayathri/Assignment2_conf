package com.company.Assignment2_conf;

/**
 * Created by Gayathri on 26/08/17.
 */
public class Session implements Registrable {

    String sessionName;
    int totalNoOfSeats;
    int noOfSeatsAvailable;
    boolean isAvailable;

    public Session(String sessionName, int totalNoOfSeats) {
        this.sessionName = sessionName;
        this.totalNoOfSeats = totalNoOfSeats;
        this.noOfSeatsAvailable = totalNoOfSeats;
        if (noOfSeatsAvailable > 0)
            isAvailable=true;
        else
            isAvailable = false;
    }

    public boolean isSeatAvailable() {
        return isAvailable;
    }

    void increaseSeatsTaken() {
        noOfSeatsAvailable--;
        if (noOfSeatsAvailable > 0 )
            isAvailable=true;
        else
            isAvailable=false;

    }
    void decreaseSeatsTaken() {
        noOfSeatsAvailable++;
        if (noOfSeatsAvailable >0 )
            isAvailable=true;
        else
            isAvailable=false;

    }
    public void register() {
        increaseSeatsTaken();
        return;

    }
    public void deregister() {
        decreaseSeatsTaken();
    }

    public String getSessionName() {
        return sessionName;
    }

    public Integer getAvailableSeats() {
        return noOfSeatsAvailable;
    }


    public void printSessionDetails() {
        System.out.println("Session Name: " + sessionName);
        System.out.println("Total No Of Seats : " + totalNoOfSeats );
        System.out.println("Available Seats : " + noOfSeatsAvailable );
    }
}
