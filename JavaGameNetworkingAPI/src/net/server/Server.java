package net.server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Server {

    private DatagramSocket socket;
    private int port;

    private boolean running;

    public Server(int port){

        this.port = port;

        try{

            socket = new DatagramSocket(port);
            running = true;
            receive();

            System.out.println("Server >> Started On Port: " + port);

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void receive(){

        Thread thread = new Thread("Waiter"){
            public void run(){

                try{

                    while(running){


                        byte[] rdata = new byte[1024];
                        DatagramPacket packet = new DatagramPacket(rdata, rdata.length);

                        socket.receive(packet);

                        String message = new String(rdata);
                        message = message.substring(0, message.indexOf("/e/"));
                        System.out.println(packet.getAddress().getHostAddress() + ":" + packet.getPort() + " >> " + message);
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }; thread.start();
    }
}
