import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Base64;
import java.io.*;

public class UDPStreamingServer {

    public static final String UDP_ADDRESS = "233.100.100.100";

    private int currentPort = 4500;
    private ArrayList<Integer> ports;
    private ArrayList<UDPStreamer> streamers;


    public UDPStreamingServer() {
        streamers = new ArrayList<>();
        ports = new ArrayList<>();
    }

    //returns the port, the user will need it to join the multicast group
    //returns 0 if an exception occurs
    public int streamVideo(String fileAddress) {
        try {
            byte[] data = encodeVideo(new File(fileAddress));
            UDPStreamer streamer = new UDPStreamer(new DatagramSocket(), data, currentPort);
            streamers.add(streamer);
            Thread t = new Thread(streamer);
            t.start();
            return currentPort++;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    //Base64 url and directory safe encoding
    public static byte[] encodeVideo(File file) throws IOException {

        Base64.Encoder encoder = Base64.getUrlEncoder();
        FileInputStream fis = new FileInputStream(file);
        byte[] bytes = new byte[(int)file.length()];
        fis.read(bytes);
        bytes = encoder.encode(bytes);
        return bytes;
    }

    //base 64 encoding test
    public static void main(String[] args)
    {
        byte[] bytes = new byte[0];
        try {
            bytes = encodeVideo(new File("/Users/pedrosalazar/Desktop/pruebas/Sample.mp4"));
            System.out.println(bytes.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
