package com.asoluter.litest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.asoluter.litest.Objects.NullObject;
import com.asoluter.litest.Objects.Strings;
import com.asoluter.litest.Objects.TypingObject;
import com.asoluter.litest.Services.Broadcasts.Broadcasts;
import com.asoluter.litest.Services.Broadcasts.Events.LoginResultEvent;
import com.asoluter.litest.Services.ServerRequest;
import com.asoluter.litest.Utility.CyclingAsync;


public class LoginActivity extends AppCompatActivity {
    SharedPreferences preferences;
    SharedPreferences.Editor prefEditor;

    private TextView title;
    private EditText mail;
    private EditText pass;
    private Button loginButton;
    private Button signupButton;
    private Toolbar toolbar;

    private BroadcastReceiver broadcastReceiver;
    Intent service;

    public static String COMMAND;

    CyclingAsync cyclingAsync;

    @Override
    protected void onDestroy() {
        unregisterReceiver(broadcastReceiver);
        super.onDestroy();
    }

    /**
     Intent loginActivity=new Intent(this,LoginActivity.class);
     loginActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

     startActivity(loginActivity);
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        preferences=getSharedPreferences("login", MODE_PRIVATE);
        prefEditor=preferences.edit();

        title=(TextView)findViewById(R.id.titleLoginView);
        mail=(EditText)findViewById(R.id.mailLoginText);
        pass=(EditText)findViewById(R.id.passLoginText);
        loginButton=(Button)findViewById(R.id.loginButton);
        signupButton=(Button)findViewById(R.id.signupButon);

        initToolbar();

        cyclingAsync=new CyclingAsync(this);

        loginButton.setOnClickListener(onClickListener);
        signupButton.setOnClickListener(onClickListener);

        broadcastReceiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                LoginResultEvent event=(LoginResultEvent)intent.getSerializableExtra(Broadcasts.BROADCAST_LOGIN);
                if(event.getLogin()){
                    //cyclingAsync.stopCycling();
                    Intent mainIntent=new Intent(LoginActivity.this,MainActivity.class);
                    mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(mainIntent);
                }
                else {
                    //cyclingAsync.stopCycling();
                    Snackbar.make(pass,getString(R.string.wrong_mail_pass),Snackbar.LENGTH_INDEFINITE)
                            .show();
                }
            }
        };
        IntentFilter filter=new IntentFilter(Broadcasts.BROADCAST_LOGIN);
        registerReceiver(broadcastReceiver,filter);

    }

    protected void initToolbar(){
        toolbar=(Toolbar)findViewById(R.id.login_toolbar);

        setSupportActionBar(toolbar);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setTitle(null);
        }

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });

        toolbar.inflateMenu(R.menu.menu_login);
    }

    private View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.loginButton:{
                    onLogin();
                    break;
                }
                case R.id.signupButon:{
                    onSignup();
                    break;
                }
            }
        }
    };

    private void onSignup(){

        saveCreds();

        Intent intent=new Intent(getApplicationContext(),SignupActivity.class);
        startActivity(intent);
    }

    private void onLogin(){

        saveCreds();


        TypingObject context=new TypingObject(Strings.AUTH, new NullObject());

        //cyclingAsync.execute();

        service=new Intent(this,ServerRequest.class);
        service.putExtra(Strings.COMMAND, context);
        startService(service);

    }


    private void saveCreds(){
        prefEditor.putString("login",mail.getText().toString());
        prefEditor.putString("pass",pass.getText().toString());
        prefEditor.apply();
    }



}
