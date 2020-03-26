import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TspiSimulator {

//    private static ServerSocket serverSocket;
//    private static Socket clientSocket;

    public static void main(String[] args) {
        if(args.length < 2) {
            throw new IllegalArgumentException("[Full message file path] [# of times to loop file]");
        }

        int loops = Integer.parseInt(args[2]);
        File tspiDataFile = new File(args[0]);
//        setup();

        for(int i=0; i<loops; i++) {
            try (BufferedReader br = new BufferedReader(new FileReader(tspiDataFile))) {
                String line;
                br.readLine(); // To skip the header of the csv
                try {
                    while ((line = br.readLine()) != null) {
                        // Send the line over the server socket
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

//        stop();
    }

//    private static void setup() throws IllegalStateException {
//        try {
//            serverSocket = new ServerSocket(59090);
//            clientSocket = serverSocket.accept();
//        } catch (IOException e) {
//            throw new IllegalStateException(e);
//        }
//    }
//
//    private static void stop() throws IllegalStateException {
//        try {
//            serverSocket.close();
//            clientSocket.close();
//        } catch (IOException e) {
//            throw new IllegalStateException(e);
//        }
//    }

}
