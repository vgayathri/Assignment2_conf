package com.company.Assignment2_conf;

import javax.lang.model.type.NullType;
import java.util.*;

/**
 * Created by Gayathri on 26/08/17.
 */
public class Conference {

    List<Session> listOfAllSessions;
    String conferenceName;
    Date conferenceDate;
    //List of emailsIds to the SessionObject
    Map <String,Session > userSessionMap = new HashMap<>();


    public Conference (String confName, Date confDate) {
        this.conferenceDate = confDate;
        this.conferenceName = confName;
        listOfAllSessions = new ArrayList<>();
        //mapOfavailableSessions.clear();
    }
    public List<Session> getSessionList() {
        return listOfAllSessions;
    }


    public void addSession(String sessionName,int noOfAvlbSeats){
        Session newSession = new Session(sessionName, noOfAvlbSeats);
        listOfAllSessions.add(newSession);

    }

    public String getConferenceName() {
        return conferenceName;
    }

    public Date getConferenceDate() {
        return conferenceDate;
    }


    public boolean addUserToSession(String userName, String sessionName){
        Session registeredSession = getSessionObject(sessionName);
        if (registeredSession ==null) {
            System.out.println("Invalid session name " + sessionName);
            return false;
        }
        if (!isValidSession(sessionName)) {
            System.out.println(sessionName + " is FULL, not available for registration");
            return false;
        }
        if (userSessionMap.containsKey(userName.toLowerCase())) {
            System.out.print("User is already registered, cannot register for another session");
            return false;
        } else {
            userSessionMap.put(userName.toLowerCase(), registeredSession);
            registeredSession.register();
            System.out.print("Session details after successful user registration\n");
            registeredSession.printSessionDetails();
            return true;
        }

    }
    public boolean removeUserFromSession(String userName) {
        if (!userSessionMap.containsKey(userName.toLowerCase())) {
            System.out.print(userName + " is NOT registered for any session, nothing to deregister!");
            return false;
        }
        Session registeredSession = userSessionMap.get(userName.toLowerCase());
        System.out.print(userName + " is being deregistered from session " + registeredSession.getSessionName());
        registeredSession.deregister();
        //remove from the mapping
        userSessionMap.remove(userName.toLowerCase());
        System.out.print("Session Status after successful de-registering\n" );
        registeredSession.printSessionDetails();
        return true;
    }

    public boolean isValidSession(String sessionName) {
        List <Session> avlbSession = getAllAvailableSessions();
        for ( Session sessionEntry : avlbSession)
        {
            if (sessionEntry.getSessionName().equalsIgnoreCase(sessionName) == true) {
                return true;
            }
        }
        return false;

    }
    public List<Session> getAllAvailableSessions() {
        List <Session> avlbSessions = new ArrayList<>();

        for ( Session sessionEntry : listOfAllSessions)
        {
            if (sessionEntry.isSeatAvailable() == true) {
                avlbSessions.add(sessionEntry);
            }
        }
        return avlbSessions;

    }

    public Session getSessionObject(String sessionName)
    {
        List <Session> avlbSessions = new ArrayList<>();

        for ( Session sessionEntry : listOfAllSessions)
        {
            if (sessionEntry.getSessionName().equalsIgnoreCase(sessionName) == true) {
               return sessionEntry;
            }
        }
        return null;
    }

    public boolean isAlreadyRegistered (String userName) {
        if (userSessionMap.containsKey(userName.toLowerCase())) {
            return true;
        } else {
            return false;
        }
    }
}
