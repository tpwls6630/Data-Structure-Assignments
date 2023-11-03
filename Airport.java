// Bongki Moon (bkmoon@snu.ac.kr)

public class Airport {

    public int portID;
    public int connectTime;

    public Airport(String port, String connectTime) {
        portID = encoder.encode(port);
        try {
            this.connectTime = Integer.parseInt(connectTime);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }

        this.connectTime = encoder.hmToMinute(this.connectTime);
    } // constructor

    public void print() {
        System.out.println(String.format("[Port Id : %d\tPort Name : %s\tConnectTime : %d]", portID,
                encoder.decode(portID), connectTime));
    }

    private static Encode encoder = new Encode();

    public static class Encode {

        private static int ENCODINGNUM = 27;
        public static int ONEDAY = 24 * 60;

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

        public int hmToMinute(int i) {
            return (i / 100) * 60 + i % 100;
        }
    }

}
