import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Base64;
import java.io.*;

public class UDPStreamingServer {

    public static final String UDP_ADDRESS = "localhost";

    private ServerSocket serverSocket;

    private ArrayList<String> ports;
    private ArrayList<DatagramSocket> sockets;


    public UDPStreamingServer() {

        ports = new ArrayList<String>();
        sockets = new ArrayList<DatagramSocket>();
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

//creo que tengo algo acá

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
