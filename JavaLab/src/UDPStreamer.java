import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;

public class UDPStreamer implements Runnable {

    public static final int MAX_SIZE = 65000; //Has a small margin for error (not the full 65507 bytes)

    private DatagramSocket socket;
    private byte[] data;
    private int port;
    private boolean on = true;


    public UDPStreamer(DatagramSocket socket, byte[] data, int port) {
        this.socket = socket;
        this.data = data;
        this.port = port;
    }

    public boolean stop() {
        on = false;
        socket.close();
        return !on;
    }


    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        System.out.println("running...");
        final int length = data.length;
        final byte[][] dest = new byte[(length + MAX_SIZE - 1) / MAX_SIZE][MAX_SIZE];
        int destIndex = 0;
        int stopIndex = 0;
        try {
            for (int startIndex = 0; startIndex + MAX_SIZE <= length && on; startIndex += MAX_SIZE) {
                stopIndex += MAX_SIZE;
                dest[destIndex++] = Arrays.copyOfRange(data, startIndex, stopIndex);
                DatagramPacket pack = new DatagramPacket(dest[destIndex], dest[destIndex].length, InetAddress.getByName(UDPStreamingServer.UDP_ADDRESS), port);
                socket.send(pack);
            }

            if (stopIndex < length) {
                dest[destIndex] = Arrays.copyOfRange(data, stopIndex, length);
                DatagramPacket pack = new DatagramPacket(dest[destIndex], dest[destIndex].length, InetAddress.getByName(UDPStreamingServer.UDP_ADDRESS), port);
                socket.send(pack);
            }

            while (on) {
                for (byte[] packet : dest) {
                    if (!on) break;
                    DatagramPacket pack = new DatagramPacket(packet, packet.length, InetAddress.getByName(UDPStreamingServer.UDP_ADDRESS), port);
                    socket.send(pack);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
