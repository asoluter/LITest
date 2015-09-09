package com.asoluter.litest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class TestActivity extends AppCompatActivity {

    private Toolbar toolbar;
    TextView questText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        initToolbar();

        questText=(TextView)findViewById(R.id.test_quest);

        setQuest();
        setAnsvers();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void initToolbar(){
        toolbar=(Toolbar)findViewById(R.id.test_toolbar);

        setSupportActionBar(toolbar);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setTitle(R.string.app_name);
        }

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });

        toolbar.inflateMenu(R.menu.menu_toolbar);
    }

    protected void setQuest(){
        questText.setText(getIntent().getStringExtra(getString(R.string.quest)));
    }

    protected void setAnsvers(){

    }
}
