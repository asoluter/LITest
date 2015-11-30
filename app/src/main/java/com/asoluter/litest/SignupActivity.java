package com.asoluter.litest;

import android.app.DatePickerDialog;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.asoluter.litest.Objects.RegData;
import com.asoluter.litest.Objects.Strings;
import com.asoluter.litest.Objects.TypingObject;
import com.asoluter.litest.Services.Broadcasts.Broadcasts;
import com.asoluter.litest.Services.ServerRequest;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class SignupActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView nameText;
    private TextView loginText;
    private TextView passText;
    private TextView mailText;
    private TextView birthText;
    private Button signUpButton;

    private SimpleDateFormat dateFormat;
    private DatePickerDialog datePickerDialog;
    private Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initToolbar();

        signUpButton=(Button)findViewById(R.id.signupButon);

        nameText=(TextView)findViewById(R.id.nameText);
        loginText=(TextView)findViewById(R.id.loginText);
        passText=(TextView)findViewById(R.id.passLoginText);
        mailText=(TextView)findViewById(R.id.mailLoginText);
        birthText=(TextView)findViewById(R.id.birthText);

        loginText.setText(getSharedPreferences("login", MODE_PRIVATE).getString("login", ""));
        passText.setText(getSharedPreferences("login",MODE_PRIVATE).getString("pass",""));


        Calendar calendar=Calendar.getInstance();
        dateFormat=new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        datePickerDialog=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate=Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                birthText.setText(dateFormat.format(newDate.getTime()));
                date=new Date(newDate.getTime().getTime());
            }
        },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));

        birthText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                signUpButton.setEnabled(true);
                if(intent.getIntExtra(Broadcasts.RESULT,-1)==0){
                    Intent mainIntent=new Intent(SignupActivity.this,MainActivity.class);
                    mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(mainIntent);
                }else Snackbar.make(signUpButton,getString(R.string.reg_not_ok),Snackbar.LENGTH_INDEFINITE).show();
            }
        };
        IntentFilter filter=new IntentFilter(Broadcasts.BROADCAST_REGISTER);
        registerReceiver(broadcastReceiver,filter);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpButton.setEnabled(false);
                TypingObject typingObject=new TypingObject(Strings.REGISTER,new RegData(nameText.getText().toString(),
                        loginText.getText().toString(),passText.getText().toString(),
                        mailText.getText().toString(),date));
                Intent service=new Intent(SignupActivity.this, ServerRequest.class);
                service.putExtra(Strings.COMMAND,typingObject);
                startService(service);
            }
        });
    }

    protected void initToolbar(){
        toolbar=(Toolbar)findViewById(R.id.signup_toolbar);

        setSupportActionBar(toolbar);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setTitle(null);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });

        toolbar.inflateMenu(R.menu.menu_signup);
    }
}
