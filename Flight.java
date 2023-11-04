// Bongki Moon (bkmoon@snu.ac.kr)

/*
 *      Flight.java
 * 
 *      Data Structure Assignment 3
 *      
 *      Author  : Sejin Woo
 *      Date    : 2023-11-03
 * 
 *      This file implement smallest element Flight
 *      Flight will be used as an Edge in graph
 */

public class Flight {

    public String departPortName; // port name
    public String arrivalPortName; // port name
    public int departPortID; // port id encoded the port name
    public int arrivalPortID; // port id encoded the port name
    public int departTime; // when this flight will depart. as minute notation
    public int arrivalTime; // when this flight will depart. as minute notation

    // Constructor
    public Flight() {
    }

    // Constructor
    public Flight(String src, String dest, String stime, String dtime) {
        departPortID = encoder.encode(src);
        arrivalPortID = encoder.encode(dest);

        departPortName = src;
        arrivalPortName = dest;

        departTime = Integer.parseInt(stime);
        arrivalTime = Integer.parseInt(dtime);

        departTime = encoder.hmToMinute(departTime);
        arrivalTime = encoder.hmToMinute(arrivalTime);
    }

    // Calculate and return total time from curTime to arrival at the destination
    // that the passenger should take
    public int totalTime(Airport port, int curTime) {

        curTime %= encoder.ONEDAY();

        int waitTime = port.connectTime; // Wait to connect this flight
        waitTime += ((departTime - (port.connectTime + curTime) % encoder.ONEDAY()) + encoder.ONEDAY())
                % encoder.ONEDAY();

        int travelTime = (arrivalTime - departTime + encoder.ONEDAY()) % encoder.ONEDAY(); // Flight time

        return waitTime + travelTime; // can exceed 24h = 1440m
    }

    public void print() {
        System.out
                .print(String.format("[%s->%s:%04d->%04d]", departPortName, arrivalPortName,
                        encoder.minuteToHM(departTime), encoder.minuteToHM(arrivalTime)));
    }

    // just for debug
    public void debug() {
        System.out.println(String.format("Flight debug\n\tdPortName : %s\n\tdPortID : %d\n\tdTime : %04d",
                departPortName, departPortID, departTime));
        System.out.println(String.format("\n\taPortName : %s\n\taPortID : %d\n\taTime : %04d",
                arrivalPortName, arrivalPortID, arrivalTime));
    }

    // Flight also need encoder from airport class
    private static Airport.Encode encoder = new Airport.Encode();
}
