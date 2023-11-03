// Bongki Moon (bkmoon@snu.ac.kr)

import java.util.ArrayList;

public class Itinerary {

    public ArrayList<Flight> tickets;

    // constructor
    public Itinerary(ArrayList<Flight> flights) {
        tickets = new ArrayList<>(flights);
    }

    public boolean isFound() {
        if (tickets.size() == 0)
            return false;
        return true;
    }

    public void print() {
        for (Flight flt : tickets) {
            flt.print();
        }
    }

}
