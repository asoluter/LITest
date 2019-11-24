package com.asoluter.litest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.asoluter.litest.Objects.AnsObject;
import com.asoluter.litest.Objects.AuthObject;
import com.asoluter.litest.Objects.Pair;
import com.asoluter.litest.Objects.Strings;
import com.asoluter.litest.Objects.TypingObject;
import com.asoluter.litest.Services.Broadcasts.Broadcasts;
import com.asoluter.litest.Services.DBHelper.DBHelper;
import com.asoluter.litest.Services.ServerRequest;
import com.asoluter.litest.Tests.Tests;
import com.asoluter.litest.Tests.TestsCover;

import java.util.ArrayList;


public class ChooseTestActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ListView chooseList;
    private Context mContext;
    private ArrayAdapter<String> dataAdapter;
    private int cont_pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_test);
        mContext =this;

        initToolbar();

        chooseList = findViewById(R.id.choose_test_list);

        setTests(getIntent().getIntExtra(
                getString(R.string.contest_pos),0));

        BroadcastReceiver ansReceiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                Intent ansIntent=new Intent(mContext, AnswerActivity.class);
                ansIntent.putExtra(Strings.TEST_RESULT,intent.getStringExtra(Strings.TEST_RESULT));
                startActivity(ansIntent);
            }
        };
        IntentFilter filter=new IntentFilter(Broadcasts.BROADCAST_ANSVER);
        registerReceiver(ansReceiver,filter);
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


        return super.onOptionsItemSelected(item);
    }

    protected void setTests(int position){
        final ArrayList<String> tests=TestsCover.getTests(position);
        dataAdapter=new ArrayAdapter<>(getApplicationContext(),
                R.layout.contest_list,
                tests);
        cont_pos=position;
        chooseList.setAdapter(dataAdapter);

        chooseList.setOnItemClickListener((parent, view, position1, id) -> {
            Intent testStart=new Intent(mContext,TestActivity.class);
            testStart.putExtra(getString(R.string.test_pos), position1);
            testStart.putExtra(getString(R.string.contest_pos), cont_pos);
            testStart.putExtra(getString(R.string.quest),
                    TestsCover.quests.get(position1));
            testStart.putExtra("test_name",tests.get(position1));
            startActivity(testStart);
        });
    }

    private static final String GET_ANSWERS ="select cont_id,test_id,ans_id from answers where ((user=?)and(cont_id=?))";

    protected void initToolbar(){
        toolbar = findViewById(R.id.choose_test_toolbar);

        setSupportActionBar(toolbar);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setTitle(R.string.tests);

        }

        toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()){
                case R.id.action_send:{
                    SQLiteDatabase database=new DBHelper(mContext).getReadableDatabase();

                    String login=getSharedPreferences("login",MODE_PRIVATE).getString("login", "");
                    AuthObject auth_obj=new AuthObject(login,getSharedPreferences("login",MODE_PRIVATE).getString("pass",""));

                    Cursor cursor=database.rawQuery(GET_ANSWERS, new String[]{login, String.valueOf(cont_pos)});
                    cursor.moveToFirst();

                    ArrayList<AnsObject> answers=new ArrayList<>();
                    if (cursor.getCount()>0)
                    do{
                        if(cursor.getInt(0)>=0&&cursor.getInt(1)>=0&&cursor.getInt(2)>=0) {
                            int cont_pos = cursor.getInt(0);
                            int cont_id = Tests.getDataBase().getCont_cont_id().get(cont_pos);
                            int test_pos = cursor.getInt(1);
                            int test_id = Tests.getTestIdFromContest(test_pos, cont_id);
                            int ans_id = cursor.getInt(2);
                            answers.add(new AnsObject(cont_id, test_id, ans_id));
                        }

                    }while (cursor.moveToNext());

                    cursor.close();

                    TypingObject typingObject=new TypingObject(Strings.TEST,new Pair(auth_obj,answers));
                    Intent intent=new Intent(mContext, ServerRequest.class);
                    intent.putExtra(Strings.COMMAND,typingObject);
                    startService(intent);

                    break;
                }
            }
            return false;
        });

        toolbar.inflateMenu(R.menu.menu_choose_test);
    }
}
