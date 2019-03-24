package infracom.server;

public class App {
    public static void main( String[] args ) {
        UDPStreamingServer server = new UDPStreamingServer();
        server.streamVideo("/Users/christianpotdevin/IdeaProjects/video2.mp4");
    }
}
