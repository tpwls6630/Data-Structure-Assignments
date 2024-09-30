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
 * 
 *      Each flight has departure port, time and arrival port, time.
 *      
 *      Every time is stored as minute. 1 hour is same to 60 minutes.
 */


public class Flight {

    public String departPortName; // port name
    public String arrivalPortName; // port name
    public int departTime; // when this flight will depart. as minute notation
    public int arrivalTime; // when this flight will depart. as minute notation

    // Constructor
    public Flight() {
    }

    // Constructor
    //
    // store arguments to field almost directly
    public Flight(String src, String dest, String stime, String dtime) {

        departPortName = src;
        arrivalPortName = dest;
        try {
            departTime = Integer.parseInt(stime);
            arrivalTime = Integer.parseInt(dtime);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }

        departTime = encoder.hmToMinute(departTime);
        arrivalTime = encoder.hmToMinute(arrivalTime);
    }

    // Calculate and return total time that the passenger should take from curTime to arrival at the destination
    //
    // curTime can exceed 24hours. So before calculation, execute modulo operation.
    // If after waiting connection time from current time exceeded departure time of this flight,
    // the passenger should take next day flight.
    // waitTime += ... is the calculation.
    //
    // TravelTime is simply arrivalTime - departTime.
    public int totalTime(Airport port, int curTime) {

        curTime %= encoder.ONEDAY;

        int waitTime = port.connectTime; // Wait to connect this flight
        waitTime += ((departTime - (port.connectTime + curTime) % encoder.ONEDAY) + encoder.ONEDAY) % encoder.ONEDAY;

        int travelTime = arrivalTime - departTime; // Flight time
        if (travelTime < 0)
        
            travelTime += encoder.ONEDAY;

        return waitTime + travelTime; // can exceed 24h = 1440m
    }

    // Print the information of this flight as specific format.
    public void print() {
        System.out
                .print(String.format("[%s->%s:%04d->%04d]", departPortName, arrivalPortName,
                        encoder.minuteToHM(departTime), encoder.minuteToHM(arrivalTime)));
    }

    // Flight also need encoder from airport class
    private static Airport.Encode encoder = new Airport.Encode();
}
