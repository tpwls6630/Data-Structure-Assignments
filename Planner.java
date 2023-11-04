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
 */

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class Planner {

    // constructor
    public Planner(LinkedList<Airport> portList, LinkedList<Flight> fltList) {

        portSize = portList.size();

        for (int i = 0; i < encoder.MAXIMUM; i++) {
            hashEncode[i] = -1;
            hashDecode[i] = -1;
        }

        this.portList = new Airport[portSize];
        for (int i = 0; i < portSize; i++) {
            this.portList[i] = portList.get(i);
        }

        // initialize hash tables
        for (int i = 0; i < portSize; i++) {

            hashEncode[this.portList[i].portID] = i;
            hashDecode[i] = this.portList[i].portID;

        }

        this.flightList = new ArrayList[portSize];
        for (int i = 0; i < portSize; i++) {
            flightList[i] = new ArrayList<>();
        }
        for (Flight flt : fltList) {

            int h = hashEncode[flt.departPortID];
            flightList[h].add(flt);

        }

    }

    public Itinerary Schedule(String start, String end, String departure) {

        int startID = hashEncode[encoder.encode(start)];
        int endID = hashEncode[encoder.encode(end)];
        int departTime = encoder.hmToMinute(Integer.parseInt(departure));

        if (startID == -1 || endID == -1) {
            return new Itinerary(new LinkedList<>());
        }

        // we assume that the first airport doesn't have
        // connection time
        int ct = portList[startID].connectTime;
        portList[startID].connectTime = 0;

        // initialize dist array as inf
        int[] dist = new int[portSize];
        for (int i = 0; i < portSize; i++) {
            dist[i] = INF;
        }
        dist[startID] = departTime;

        Flight[] preFlight = new Flight[portSize];
        for (int i = 0; i < portSize; i++) {
            preFlight[i] = null;
        }

        PriorityQueue<Pair> pq = new PriorityQueue<>();
        pq.add(new Pair(departTime, startID));

        while (!pq.isEmpty()) {

            Pair cur = pq.poll();
            int d = cur.first;
            int curNode = cur.second;

            if (d > dist[curNode])
                continue;
            for (Flight flt : flightList[curNode]) {
                int nextNode = hashEncode[flt.arrivalPortID];
                int w = flt.totalTime(portList[curNode], d);
                if (dist[nextNode] > dist[curNode] + w) {
                    dist[nextNode] = dist[curNode] + w;
                    preFlight[nextNode] = flt;
                    pq.add(new Pair(dist[nextNode], nextNode));
                }
            }
        }

        LinkedList<Flight> itinerary = new LinkedList<>();
        while (preFlight[endID] != null) {
            itinerary.addFirst(preFlight[endID]);
            endID = hashEncode[preFlight[endID].departPortID];
        }

        // debug
        // System.out.println("Debug : distances");
        // for (int i = 0; i < portSize; i++) {
        // System.out.println(String.format("\tDist[%d] : %d", i, dist[i]));
        // }

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
            return this.first - p.first;
        }
    }

    private static int INF = Integer.MAX_VALUE;
    private Airport[] portList;
    private ArrayList<Flight>[] flightList;

    private int[] hashEncode = new int[encoder.MAXIMUM];
    private int[] hashDecode = new int[encoder.MAXIMUM];

    private int portSize;

    private static Airport.Encode encoder = new Airport.Encode();
}
