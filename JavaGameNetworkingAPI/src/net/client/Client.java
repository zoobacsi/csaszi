package net.client;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Client {

    private static DatagramSocket socket;

    public static void start(){

        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public static void send(String message, String ip, int port){

        try{

            message += "/e/";
            byte[] data = message.getBytes();

            DatagramPacket packet = new DatagramPacket(data, data.length, InetAddress.getByName(ip), port);

            socket.send(packet);

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
