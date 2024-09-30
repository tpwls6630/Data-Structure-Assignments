// Bongki Moon (bkmoon@snu.ac.kr)

/*
 *      Planner.java
 * 
 *      Data Structure Assignment 3
 *      
 *      Author  : Sejin Woo
 *      Date    : 2023-11-03
 * 
 *      Core of this assignment.
 *      For a given query(consist of departure airport, destination airport, departure time)
 *      this class should return the shortest time path of the travel 
 *      by using Dijkstra algorithm.
 * 
 *      By using hash map, each port can be accessed by name, and can be stored in array.
 */

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.HashMap;

public class Planner {

    // Constructor
    //
    // List of ports is stored in array(=portList).
    // Each port name is correspond to a index by hash map(=hashTable),
    // and the index can be used to access the correspond port or flights.
    // Flights are stored in array(=flightList) of arraylist (2-dim array), which the row of table is departure airport of the flight.
    // (= Flights of same row is that they have same departure airport.)
    //
    @SuppressWarnings("unchecked")
    public Planner(LinkedList<Airport> portList, LinkedList<Flight> fltList) {

        portSize = portList.size();
        hashTable = new HashMap<>();

        this.portList = new Airport[portSize];

        // initialize hash tables
        for (int i = 0; i < portSize; i++) {
            this.portList[i] = portList.get(i);

            hashTable.put(portList.get(i).portName, i);

        }

        // initialize edge table as linked list.
        this.flightList = new ArrayList[portSize];
        for (int i = 0; i < portSize; i++) {
            flightList[i] = new ArrayList<>();
        }
        for (Flight flt : fltList) {

            int h = hashTable.get(flt.departPortName);
            flightList[h].add(flt);

        }

    }

    // Find minimum time from start port to end port by using dijkstra algorithm.
    // 
    // If there is no port of start or end in hash table, there is no path.
    // So just return empty path.
    //
    // Even start and end port are same, different departure time derive different answer.
    // So we need to access the weight of edge in runtime, especially in O(1) time.
    // Accessing the weight in O(1) time is implemented in Flight class. (Flight.totalTime())
    //
    // To trace exact path, every node(airport) store a flight which have the airport as a arrival airport.
    // For example, a flight of ICN(0010) -> KIX(0210) is stored in KIX node.
    // When tracing the path, we trace from end airport to start airport. (KIX, ICN, ...)
    // Reversing the tracing, we can get answer.
    public Itinerary Schedule(String start, String end, String departure) {


        // If there are no vertex of inputs in airport table, there is no path.
        if (hashTable.containsKey(start) == false || hashTable.containsKey(end) == false) {
            return new Itinerary(new LinkedList<>());
        }

        // String is modified as integer.
        int startID = hashTable.get(start);
        int endID = hashTable.get(end);
        int departTime = encoder.hmToMinute(Integer.parseInt(departure));

        // we assume that the first airport doesn't have connection time
        int ct = portList[startID].connectTime;
        portList[startID].connectTime = 0;

        // Initialize distance table
        int[] dist = new int[portSize];
        for (int i = 0; i < portSize; i++) {
            dist[i] = INF;
        }
        dist[startID] = departTime;

        // preFlight store previous edge to track exact path of travel.
        // Initialize table
        Flight[] preFlight = new Flight[portSize];
        for (int i = 0; i < portSize; i++) {
            preFlight[i] = null;
        }

        // Priority queue is used in order to find nearest vertex from starting node.
        // pq get pair as {distance from s, v}
        PriorityQueue<Pair> pq = new PriorityQueue<>();
        pq.add(new Pair(departTime, startID));

        // Dijkstra
        while (!pq.isEmpty()) {

            Pair cur = pq.poll();
            int d = cur.first;
            int curNode = cur.second;

            // not use '>=' but use '>'
            // The last element in pq which has same distance should have longer path(means larger connection)
            // If we want less connection, the first path which updated shortest distance is answer.
            if (d > dist[curNode])
                continue;

            for (Flight flt : flightList[curNode]) {

                int nextNode = hashTable.get(flt.arrivalPortName);
                int w = flt.totalTime(portList[curNode], d);
                if (dist[nextNode] > dist[curNode] + w) {

                    dist[nextNode] = dist[curNode] + w;
                    preFlight[nextNode] = flt;
                    pq.add(new Pair(dist[nextNode], nextNode));

                }

            }
        }

        // Find and store exact path of travel as array.
        LinkedList<Flight> itinerary = new LinkedList<>();
        while (preFlight[endID] != null) {
            itinerary.addFirst(preFlight[endID]);
            endID = hashTable.get(preFlight[endID].departPortName);
        }

        // restore first airport connectTime
        portList[startID].connectTime = ct;

        return new Itinerary(itinerary);
    }

    private static class Pair implements Comparable<Pair> {

        public int first, second;

        public Pair(int first, int second) {
            this.first = first;
            this.second = second;
        }

        @Override
        public int compareTo(Pair p) {
            if (this.first < p.first) {
                return -1;
            } else if (this.first > p.first) {
                return 1;
            }
            return 0;
        }
    }

    private final int INF = Integer.MAX_VALUE;
    private Airport[] portList;
    private ArrayList<Flight>[] flightList;

    private HashMap<String, Integer> hashTable;
    private int portSize;

    private Airport.Encode encoder = new Airport.Encode();
}
