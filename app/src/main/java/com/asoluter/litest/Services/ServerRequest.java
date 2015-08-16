package com.asoluter.litest.Services;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;

import com.asoluter.litest.LoginActivity;
import com.asoluter.litest.Objects.AuthObject;
import com.asoluter.litest.Objects.Strings;
import com.asoluter.litest.Objects.TypingObject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerRequest extends Service {

    ExecutorService executorService;

    @Override
    public void onCreate() {
        super.onCreate();
        executorService= Executors.newFixedThreadPool(2);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        MyAsync myAsync=new MyAsync();
        myAsync.execute((TypingObject)intent.getSerializableExtra("obj"));

        return super.onStartCommand(intent, flags, startId);
    }



    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    private class MyAsync extends AsyncTask<TypingObject,Void,Void>{
        private  Socket socket;
        private  ObjectOutputStream out;
        private  ObjectInputStream in;
        private  boolean online;
        TypingObject typingObject;

        @Override
        protected Void doInBackground(TypingObject... params) {
            typingObject=params[0];
            runS();
            deBundle();
            stopS();
            return null;
        }
        private void deBundle(){
            switch (typingObject.getType()){
                case Strings.AUTH:{
                    login((AuthObject)typingObject.getObject());
                    break;
                }
                case Strings.TEST:{
                    login((AuthObject)typingObject.getObject());
                    break;
                }
            }
        }

        private void runS(){

            try {
                SocketAddress socketAddress=new InetSocketAddress("192.168.1.7",8000);
                //socket=new Socket("192.168.1.7",8080);
                socket=new Socket();
                online=true;
                socket.connect(socketAddress,10000);

            } catch (IOException e) {
                e.printStackTrace();
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

        private  void stopS(){
            try {
                socket.close();
                online=false;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



        private void login(AuthObject authObject){
            int res=0;

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
            }

            Intent intent=new Intent(LoginActivity.BROADCAST_ACTION);
            intent.putExtra(LoginActivity.RESULT,res);
            sendBroadcast(intent);
        }
    }


}
