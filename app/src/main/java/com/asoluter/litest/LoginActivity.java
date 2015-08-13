package com.asoluter.litest;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.asoluter.litest.Objects.AuthObject;
import com.asoluter.litest.Services.ServerRequest;

public class LoginActivity extends AppCompatActivity {

    private Typeface font;
    private TextView title;
    private EditText mail;
    private EditText pass;
    private Button loginButton;
    private Button signupButton;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        title=(TextView)findViewById(R.id.titleLoginView);
        mail=(EditText)findViewById(R.id.mailLoginText);
        pass=(EditText)findViewById(R.id.passLoginText);
        loginButton=(Button)findViewById(R.id.loginButton);
        signupButton=(Button)findViewById(R.id.signupButon);

        initToolbar();

        loginButton.setOnClickListener(onClickListener);
        signupButton.setOnClickListener(onClickListener);

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
        Intent intent=new Intent(getApplicationContext(),SignupActivity.class);
        intent.putExtra(getString(R.string.login_label),mail.getText().toString());
        intent.putExtra(getString(R.string.pass_label),pass.getText().toString());
        startActivity(intent);
    }

    private void onLogin(){
        AuthObject authObject=new AuthObject(mail.getText().toString(),pass.getText().toString());
        int ans=ServerRequest.login(authObject);
        if(ans==666)mail.setText("All is Connected)",TextView.BufferType.EDITABLE);
        else mail.setText("Baaaaaad", TextView.BufferType.EDITABLE);
    }
}
