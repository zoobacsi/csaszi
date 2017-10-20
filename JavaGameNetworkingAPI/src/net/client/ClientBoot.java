package net.client;

public class ClientBoot {

    private static String ip = "localhost";
    private static int port = 8111;

    public ClientBoot(){

    }

    public static void main(String[] args){

        Client.start();
        Client.send("Hello Network This is a Test", ip, port);
        Client.send("Hellvdso Network This is a Test", ip, port);
        Client.send("Hedsgasllo Network This is a Test", ip, port);
        Client.send("Hello Network fasdfdThis is a Test", ip, port);
    }
}
