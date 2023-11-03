// Bongki Moon (bkmoon@snu.ac.kr)

/*
 *      Itinerary.java
 * 
 *      Data Structure Assignment 3
 *      
 *      Author  : Sejin Woo
 *      Date    : 2023-11-03
 * 
 *      This class has a series of flights.
 *      Each flights is equal to a correspond ticket.
 */

import java.util.ArrayList;
import java.util.LinkedList;

public class Itinerary {

    public ArrayList<Flight> tickets;

    // constructor
    public Itinerary(LinkedList<Flight> flights) {
        tickets = new ArrayList<>(flights);
    }

    public boolean isFound() {
        if (tickets.size() == 0)
            return false;
        return true;
    }

    public void print() {
        if (tickets.size() == 0) {
            System.out.print("No Flight Schedule Found.");
        }

        for (Flight flt : tickets) {
            flt.print();
        }
        System.out.println("");
    }

}
