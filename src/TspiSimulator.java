import java.io.*;
import java.net.*;

public class TspiSimulator {
    
    public static void main(String[] args) {
        if(args.length < 2) {
            throw new IllegalArgumentException("[Full message file path] [# of times to loop file]");
        }

        int loops = Integer.parseInt(args[1]);
        File tspiDataFile = new File(args[0]);

        try(DatagramSocket socket_A = new DatagramSocket(59091);
            DatagramSocket socket_B = new DatagramSocket(59092);
            DatagramSocket socket_C = new DatagramSocket(59093)) {
            for (int i = 0; i < loops; i++) {
                try (BufferedReader br = new BufferedReader(new FileReader(tspiDataFile))) {
                    String line;
                    br.readLine(); // To skip the header of the csv
                    try {
//                        long startTime = System.currentTimeMillis();
                        while ((line = br.readLine()) != null) {
                            // Send the line over the server socket
                            String[] vals = line.split(",");
                            //timeSec,trackE,trackF,trackG,A_mode,A_rg,A_az,A_el,B_mode,B_rg,B_az,B_el,C_mode,C_rg,C_az,Cel
                            // 0        1       2       3   4       5   6   7       8   9     10   11   12      13  14  15
                            String msg_A = vals[0] + "," + vals[1] + "," + vals[2] + "," + vals[3] + ","
                                    + vals[4] + "," + vals[5] + "," + vals[6] + "," + vals[7];
                            String msg_B = vals[0] + "," + vals[1] + "," + vals[2] + "," + vals[3] + ","
                                    + vals[8] + "," + vals[9] + "," + vals[10] + "," + vals[11];
                            String msg_C = vals[0] + "," + vals[1] + "," + vals[2] + "," + vals[3] + ","
                                    + vals[12] + "," + vals[13] + "," + vals[14] + "," + vals[15];

                            byte[] bytes_A = msg_A.getBytes();
                            byte[] bytes_B = msg_B.getBytes();
                            byte[] bytes_C = msg_C.getBytes();
                            DatagramPacket packet = new DatagramPacket(bytes_A, bytes_A.length, InetAddress.getLocalHost(), 59090);
                            socket_A.send(packet);
                            packet = new DatagramPacket(bytes_B, bytes_B.length, InetAddress.getLocalHost(), 59090);
                            socket_B.send(packet);
                            packet = new DatagramPacket(bytes_C, bytes_C.length, InetAddress.getLocalHost(), 59090);
                            socket_C.send(packet);
                            System.out.println("(" + i + ") Sent line: " + line);
                            Thread.sleep(19);
                        }
//                        System.out.println(System.currentTimeMillis() - startTime);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}