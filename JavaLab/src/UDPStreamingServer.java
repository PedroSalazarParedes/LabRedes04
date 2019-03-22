import java.util.Base64;
import java.io.*;

public class UDPStreamingServer {


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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
