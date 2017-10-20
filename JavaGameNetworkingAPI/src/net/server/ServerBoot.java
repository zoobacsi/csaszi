package net.server;

public class ServerBoot {


    public ServerBoot(int port){
        new Server(port);
    }

    public static void main(String[] args){

        if  (args.length != 1) {
            System.err.println("ServerBoot >> Error Invalid Paramteres Please Enter a Port");
            return;
            //System.exit(-1);
        }

        int port = Integer.parseInt(args[0]);
        new ServerBoot(port);
    }
}
