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
 */

public class Airport {

    public int portID; // Encode port name as integer (ex. "ZZZ" -> 19682)
    public int connectTime; // Time a passenger should wait to access next flight.
                            // Saved as minutes (ex. 1h40m -> 100m)

    // Constructor
    public Airport(String port, String connectTime) {
        portID = encoder.encode(port);
        try {
            this.connectTime = Integer.parseInt(connectTime);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }

        this.connectTime = encoder.hmToMinute(this.connectTime);
    }

    // Print information of this airport
    public void print() {
        System.out.println(String.format("[Port Id : %d\tPort Name : %s\tConnectTime : %d]", portID,
                encoder.decode(portID), connectTime));
    }

    // I made a instance of Encode in order to use port name encoding/decoding
    private static Encode encoder = new Encode();

    /*
     * This class implement port name <-> port id encode/decode
     * And declare some useful constants
     * 
     * This class will be used in other classes
     */
    public static class Encode {

        private static int ENCODINGNUM = 27;// 3-letter port name will be encoded as radix 27.
                                            // Minimum is "AAA" = 757 and maximum is "ZZZ" = 19682
        public static int ONEDAY = 24 * 60; // One day is surely 1440 minutes

        public int encode(String str) {
            int ret = 0;
            for (int i = 0; i < str.length() - 1; i++) {
                ret += str.charAt(i) - 'A' + 1;
                ret *= ENCODINGNUM;
            }
            ret += str.charAt(str.length() - 1) - 'A' + 1;
            return ret;
        }

        public String decode(int i) {
            int p = i;
            String ret = new String();
            while (p > 0) {
                ret = (char) ((p % ENCODINGNUM) + 'A' - 1) + ret;
                p /= ENCODINGNUM;
            }
            return ret;
        }

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
