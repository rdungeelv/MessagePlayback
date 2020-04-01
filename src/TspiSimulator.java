import java.io.*;
import java.net.*;

public class TspiSimulator {
    
    public static void main(String[] args) {
        if(args.length < 2) {
            throw new IllegalArgumentException("[Full message file path] [# of times to loop file]");
        }

        int loops = Integer.parseInt(args[1]);
        File tspiDataFile = new File(args[0]);

        try(DatagramSocket socket = new DatagramSocket(59091)) {
            for (int i = 0; i < loops; i++) {
                try (BufferedReader br = new BufferedReader(new FileReader(tspiDataFile))) {
                    String line;
                    br.readLine(); // To skip the header of the csv
                    try {
//                        long startTime = System.currentTimeMillis();
                        while ((line = br.readLine()) != null) {
                            // Send the line over the server socket
                            byte[] bytes = line.getBytes();
                            DatagramPacket packet = new DatagramPacket(bytes, bytes.length, InetAddress.getLocalHost(), 59090);
                            socket.send(packet);
//                            System.out.println("(" + i + ") Sent line: " + line);
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