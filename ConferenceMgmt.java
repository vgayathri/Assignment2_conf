package com.company.Assignment2_conf;

import java.io.*;
import java.util.*;
/**
 * Created by Gayathri on 27/08/17.
 */
public class ConferenceMgmt {

    public Conference myConf;
    String sessionCsvFile;
    String userActionCsvFile;

    public ConferenceMgmt() {
        myConf = new Conference("MyConference", new Date());
        sessionCsvFile = "/users/Gayathri/SessionDetails.csv";
        userActionCsvFile = "/users/Gayathri/ConfMgmt.csv";
    }

    public ConferenceMgmt(String SessionCsv, String UserActionCsv) {
        myConf = new Conference("MyConference", new Date());
        sessionCsvFile = SessionCsv;
        userActionCsvFile = UserActionCsv;
    }

    public void printAllAvailableSessions() {
        List<Session> allSessions = new ArrayList<Session>();
        System.out.println("Getting all the available sessions");
        System.out.println("-----------------------------------");
        allSessions = myConf.getAllAvailableSessions();
        for (Session aSession : allSessions) {
            aSession.printSessionDetails();
        }
    }

    public boolean checkValidEmail(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    public void setSessionCsvFile(String csvFile) {
        this.sessionCsvFile = csvFile;
    }

    public void setUserCsvFile(String csvFile) {
        this.userActionCsvFile = csvFile;
    }

    //Method to be invoked when session is taken from stdin

    public void registerUser(String userEmail) {
        // Ask the user to select a session
        Scanner sessIn = new Scanner(System.in);

        System.out.println("Please enter the session you wish to");
        String selectedSession = sessIn.next();
        boolean returnStatus = myConf.addUserToSession(userEmail.toLowerCase(), selectedSession);

    }
    //Method to be invoked when session is taken from CSV file

    public void registerUser(String userEmailId, String sessionName) {

        if (checkValidEmail(userEmailId) == false) {
            System.out.println("Email address entered" + userEmailId + " is invalid!!");
            return;
        }
        if (myConf.isAlreadyRegistered(userEmailId)) {
            System.out.println(userEmailId + " is already registered for a session!");
            return;
        }
        boolean returnStatus = myConf.addUserToSession(userEmailId.toLowerCase(), sessionName);
    }


    public void deRegisterUserFromASession(String userEmail) {
        // Ask the user to select a session
        boolean returnStatus = myConf.removeUserFromSession(userEmail.toLowerCase());
        if (returnStatus) {
            System.out.println("User successfully deregistered");
        }

    }


    public void popoulateSessions() {

        String line = "";
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(sessionCsvFile))) {

            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] sessionLine = line.split(cvsSplitBy);
                myConf.addSession(sessionLine[0],Integer.parseInt(sessionLine[1]));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void populateUserActions() {

        String line = "";
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(userActionCsvFile))) {

            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] userActivity = line.split(cvsSplitBy);

                /**
                 * R,userEmail,SessionName - to register
                 * D,userEmail            - to deregister
                 **/

                String action = userActivity[0];
                String emailId = userActivity[1].toLowerCase();
                switch (action.toLowerCase()){
                    case "r":

                        String sessionName = userActivity[2].toLowerCase();
                        System.out.println("\nRegistering user " + emailId + " for Session : " + sessionName);
                        registerUser(emailId, sessionName);
                        break;
                    case "d":
                        System.out.println("\nDeRegistering user " + emailId );

                        deRegisterUserFromASession(emailId);
                        break;
                    default:
                        System.out.println("Unsupported action/Illegal file format");

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ConferenceMgmt myConferenceMgmt = new ConferenceMgmt();
        myConferenceMgmt.popoulateSessions();
        myConferenceMgmt.printAllAvailableSessions();
        myConferenceMgmt.populateUserActions();

    }
}
