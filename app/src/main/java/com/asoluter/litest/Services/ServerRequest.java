package com.asoluter.litest.Services;

import com.asoluter.litest.Objects.AuthObject;
import com.asoluter.litest.Objects.Strings;
import com.asoluter.litest.Objects.TypingObject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;

public class ServerRequest {

    private static Socket socket;
    private static ObjectOutputStream out;
    private static ObjectInputStream in;
    private static boolean online;

    private static void run(){

        try {
            SocketAddress socketAddress=new InetSocketAddress("192.168.1.7",8000);
            socket=new Socket();
            online=true;
            socket.connect(socketAddress,10000);

        } catch (IOException e) {
            online=false;
        }

        if (online) {
            try {
                in= (ObjectInputStream) socket.getInputStream();
                out= (ObjectOutputStream) socket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private static void stop(){
        try {
            socket.close();
            online=false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int login(AuthObject authObject){
        int res=0;

        run();
        if(online) {
            try {
                        out.writeObject(new TypingObject(Strings.TEST,authObject));

                        try {
                            TypingObject ret=(TypingObject)in.readObject();
                            if(ret.getType().equals(Strings.TEST))res=666;
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            stop();
        }




        return res;
    }
}
