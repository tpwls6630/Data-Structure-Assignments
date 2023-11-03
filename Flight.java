// Bongki Moon (bkmoon@snu.ac.kr)

public class Flight {

    public String departPortName;
    public String arrivalPortName;
    public int departPortID;
    public int arrivalPortID;
    public int departTime;
    public int arrivalTime;

    // constructor
    public Flight(String src, String dest, String stime, String dtime) {
        departPortID = encoder.encode(src);
        arrivalPortID = encoder.encode(dest);
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

    public int totalTime(Airport port, int curTime) {
        int waitTime = departTime - (port.connectTime + curTime);
        if (waitTime < 0)
            waitTime += encoder.ONEDAY;

        int travelTime = arrivalTime - departTime;
        if (travelTime < 0)
            travelTime += encoder.ONEDAY;

        return waitTime + travelTime; // can exceed 24h
    }

    public void print() {
        System.out
                .print(String.format("[%s->%s:%04d->%04d]", departPortName, arrivalPortName, departTime, arrivalTime));
    }

    public void debug() {
        System.out.println(String.format("Flight debug\n\tdPortName : %s\n\tdPortID : %d\n\tdTime : %04d",
                departPortName, departPortID, departTime));
        System.out.println(String.format("\n\taPortName : %s\n\taPortID : %d\n\taTime : %04d",
                arrivalPortName, arrivalPortID, arrivalTime));
    }

    private static Airport.Encode encoder = new Airport.Encode();
}
