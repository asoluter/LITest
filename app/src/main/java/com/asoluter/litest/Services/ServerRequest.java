package com.asoluter.litest.Services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.IBinder;

import com.asoluter.litest.Objects.AuthObject;
import com.asoluter.litest.Objects.DataBase;
import com.asoluter.litest.Objects.NullObject;
import com.asoluter.litest.Objects.Strings;
import com.asoluter.litest.Objects.TypingObject;
import com.asoluter.litest.Services.Broadcasts.Broadcasts;
import com.asoluter.litest.Services.Broadcasts.Events.LoginResultEvent;
import com.asoluter.litest.Services.Broadcasts.Events.RefreshResultEvent;
import com.asoluter.litest.Tests.Tests;

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
        executorService= Executors.newFixedThreadPool(3);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        MyAsync myAsync=new MyAsync();
        TypingObject type=(TypingObject)intent.getSerializableExtra(Strings.COMMAND);
        myAsync.execute(type);
        //myAsync.execute();
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
        private SharedPreferences preferences;


        @Override
        protected Void doInBackground(TypingObject... params) {
            typingObject=params[0];
            runS();
            if(online){
                deBundle();
                stopS();
            }
            else {
                Intent intent=new Intent(Broadcasts.BROADCAST_LOGIN);
                intent.putExtra(Broadcasts.BROADCAST_LOGIN,new LoginResultEvent(false,false));
                sendBroadcast(intent);
            }

            stopSelf();
            return null;
        }



        @Override
        protected void onPreExecute() {
            preferences=getSharedPreferences("login",MODE_PRIVATE);
        }

        private void deBundle(){
            switch (typingObject.getType()){
                case Strings.AUTH:{
                    Intent intent=new Intent(Broadcasts.BROADCAST_LOGIN);
                    
                    intent.putExtra(Broadcasts.BROADCAST_LOGIN,new LoginResultEvent(login(),true));
                    sendBroadcast(intent);

                    break;
                }
                case Strings.REFRESH:{
                    Intent intent=new Intent(Broadcasts.BROADCAST_REFRESH);
                    intent.putExtra(Broadcasts.BROADCAST_REFRESH,new RefreshResultEvent(refresh()));
                    sendBroadcast(intent);

                    break;
                }
                case Strings.TEST:{
                    login();
                    break;
                }
            }
        }

        private void runS(){
            online=false;
            try {
                ConnectivityManager conManager=
                        (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNet=conManager.getActiveNetworkInfo();
                if(activeNet!=null){
                    if(activeNet.isAvailable()){
                        SocketAddress socketAddress=new InetSocketAddress("192.168.1.7",8000);
                        socket=new Socket();
                        online=true;
                        socket.connect(socketAddress,5000);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
                online=false;
            }

            if (online) {
                try {
                    in= new ObjectInputStream(socket.getInputStream()) ;
                    out=new ObjectOutputStream(socket.getOutputStream());
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



        private boolean login(){

            AuthObject authObject=new AuthObject(preferences.getString("login",""),preferences.getString("pass",""));
            TypingObject typingObject=new TypingObject(Strings.AUTH,authObject);

            if(online) {
                try {
                    out.writeObject(typingObject);

                    try {
                        TypingObject ret=(TypingObject)in.readObject();
                        if(ret.getType().equals(Strings.OK))return true;
                        else if(ret.getType().equals(Strings.ERROR))return false;
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return false;
        }

        private boolean refresh(){
            if(online){
                try {
                    out.writeObject(new TypingObject(Strings.REFRESH,new NullObject()));
                    try {
                        TypingObject ret=(TypingObject)in.readObject();
                        ret.getType();
                        if (ret.getType().equals(Strings.DATABASE)){
                            DataBase data=(DataBase)ret.getObject();
                            Tests.setDataBase(data);

                            return true;
                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return false;
        }
    }


}
