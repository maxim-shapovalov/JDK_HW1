public class Main {
    public static void main(String[] args) {
        ServerWindow serverWindow = new ServerWindow();
        ClientGUI clientGUI = new ClientGUI(serverWindow);
        ClientGUI clientGUI1 = new ClientGUI(serverWindow);
    }
}
