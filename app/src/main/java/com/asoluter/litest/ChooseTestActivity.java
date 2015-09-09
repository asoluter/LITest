package com.asoluter.litest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.asoluter.litest.Tests.TestsCover;


public class ChooseTestActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ListView chooseList;
    Context context;
    private ArrayAdapter<String> dataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_test);
        context=this;

        initToolbar();

        chooseList=(ListView)findViewById(R.id.choose_test_list);

        setTests(getIntent().getIntExtra(
                getString(R.string.contest_pos)
                ,0));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_choose_test, menu);
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

    protected void setTests(int position){
        dataAdapter=new ArrayAdapter<String>(getApplicationContext(),
                R.layout.contest_list,
                TestsCover.getTests(position));

        chooseList.setAdapter(dataAdapter);

        chooseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent testStart=new Intent(context,TestActivity.class);
                testStart.putExtra(getString(R.string.test_pos), position);
                testStart.putExtra(getString(R.string.quest),
                        TestsCover.quests.get(position));
                startActivity(testStart);
            }
        });
    }

    protected void initToolbar(){
        toolbar=(Toolbar)findViewById(R.id.choose_test_toolbar);

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
}
