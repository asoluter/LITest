package com.asoluter.litest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.asoluter.litest.Objects.NullObject;
import com.asoluter.litest.Objects.Strings;
import com.asoluter.litest.Objects.TypingObject;
import com.asoluter.litest.Services.Broadcasts.Broadcasts;
import com.asoluter.litest.Services.Broadcasts.Events.RefreshResultEvent;
import com.asoluter.litest.Services.ServerRequest;
import com.asoluter.litest.Tests.Tests;
import com.asoluter.litest.Tests.TestsCover;



public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private Toolbar toolbar;
    private ListView contestList;
    private SwipeRefreshLayout refreshLayout;
    private Context context;

    private ArrayAdapter<String> dataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=this;

        initToolbar();
        refreshLayout = findViewById(R.id.mainRefreshLayout);
        contestList = findViewById(R.id.contestList);

        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeResources(R.color.blue,
                R.color.green,
                R.color.yellow,
                R.color.red);

        BroadcastReceiver mainReceiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                RefreshResultEvent event=(RefreshResultEvent)
                        intent.getSerializableExtra(Broadcasts.BROADCAST_REFRESH);
                refreshResult(event);
            }
        };
        IntentFilter filter=new IntentFilter(Broadcasts.BROADCAST_REFRESH);
        registerReceiver(mainReceiver,filter);

        updateTestBase();

    }

    public void refreshResult(RefreshResultEvent event){
        if (event.getStatus()){

            dataAdapter= new ArrayAdapter<>(getApplicationContext(),
                    R.layout.contest_list,
                    TestsCover.getContests());

            contestList.setAdapter(dataAdapter);

            contestList.setOnItemClickListener((parent, view, position, id) -> {
                Intent testsStart = new Intent(context, ChooseTestActivity.class);
                testsStart.putExtra(getString(R.string.contest_pos), position);
                startActivity(testsStart);
            });

            refreshLayout.setRefreshing(false);
        }else {
            Snackbar.make(contestList,getString(R.string.error_loading_data),Snackbar.LENGTH_LONG)
                    .show();
        }
    }

    protected void initToolbar(){
        toolbar = findViewById(R.id.main_toolbar);
        
        toolbar.setTitle(R.string.app_name);

        toolbar.setOnMenuItemClickListener(item -> false);

        toolbar.inflateMenu(R.menu.menu_toolbar);
    }

    protected void updateTestBase(){
        TypingObject context=new TypingObject(Strings.REFRESH,new NullObject());

        Intent service=new Intent(this,ServerRequest.class);
        service.putExtra(Strings.COMMAND, context);
        startService(service);
    }

    @Override
    public void onRefresh() {
        updateTestBase();
    }
}
