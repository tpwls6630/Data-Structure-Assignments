// Bongki Moon (bkmoon@snu.ac.kr)

/*
 *      Airport.java
 * 
 *      Data Structure Assignment 3
 *      
 *      Author  : Sejin Woo
 *      Date    : 2023-11-03
 * 
 *      This file implement smallest element Airport
 *      Airport will be used as a Vertex in graph
 * 
 *      Each airport has port name and connection time.
 *      Connection time is that a passenger should wait to take next flight.  
 *      Connection time is stored as minutes. 1 hour is same to 60 minutes.
 *      
 */

public class Airport {

    public String portName; // Name of this port.
    public int connectTime; // Time a passenger should wait to access next flight.
                            // Saved as minutes (ex. 1h40m -> 100m)

    // Constructor
    //
    // Arguments are stored to field almost directly.
    public Airport(String port, String connectTime) {

        portName = port;
        try {
            this.connectTime = Integer.parseInt(connectTime);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }

        this.connectTime = encoder.hmToMinute(this.connectTime);
    }

    // Print information of this airport
    public void print() {
    }

    // I made a instance of Encode in order to use port name encoding/decoding
    private static Encode encoder = new Encode();

    /*
     * This class implement transformation between minutes expression and hours-minutes expression.
     * 
     * This class will be used in other classes
     */
    public static class Encode {

        public final int ONEDAY = 24 * 60; // One day is surely 1440 minutes

        // Hour-minute notation to only minute notation
        // ex. 1h 40m -> 100m
        public int hmToMinute(int i) {
            return (i / 100) * 60 + i % 100;
        }

        public int minuteToHM(int m) {
            return (m / 60) * 100 + m % 60;
        }

    }

}
