package com.asoluter.litest;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.asoluter.litest.Objects.Strings;
import com.asoluter.litest.Services.DBHelper.DBHelper;
import com.asoluter.litest.Tests.TestsCover;

import java.util.ArrayList;


public class TestActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ArrayList<RadioButton> radioButtons;
    private TextView questText;
    private LinearLayout chooseLayout;
    private SQLiteDatabase database;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        initToolbar();

        questText= findViewById(R.id.quest_text);

        setQuest();
        setAnsvers();
        database=new DBHelper(this).getWritableDatabase();
        preferences=getSharedPreferences("login", MODE_PRIVATE);

        toggleChecked();
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

        return super.onOptionsItemSelected(item);
    }

    private void initToolbar(){
        toolbar = findViewById(R.id.test_toolbar);

        setSupportActionBar(toolbar);

        if(getSupportActionBar()!=null){
            int test_id = getIntent().getIntExtra(getString(R.string.test_pos),-1);
            getSupportActionBar().setTitle(getIntent().getStringExtra("test_name"));
        }

        toolbar.setOnMenuItemClickListener(item -> false);

        toolbar.inflateMenu(R.menu.menu_toolbar);
    }

    private void setQuest(){
        questText.setText(getIntent().getStringExtra(getString(R.string.quest)));
    }

    private void setAnsvers(){
        Bundle ansBundle= TestsCover.getAnsvers(
                getIntent().getIntExtra(getString(R.string.test_pos), 0));
        ArrayList<String> ansvers=ansBundle.getStringArrayList(Strings.ANSVERS);
        ArrayList<Integer> ans_id=ansBundle.getIntegerArrayList(Strings.ANS_ID);
        radioButtons=new ArrayList<>();

        chooseLayout = findViewById(R.id.choose_test_list);
        LayoutInflater inflater=(LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);

        if (ans_id != null&&ansvers!=null) {
            int ans_id_size=ans_id.size();
            for(int i=0;i<ans_id_size;i++){
                LinearLayout card=(LinearLayout)inflater.inflate(R.layout.merge_ansver_card,null);
                RadioButton radio = card.findViewById(R.id.radio_ans);
                radio.setTag(ans_id.get(i));
                card.setTag(ans_id.get(i));
                TextView ans_text = card.findViewById(R.id.text_ans);
                ans_text.setText(ansvers.get(i));

                radio.setOnClickListener(onClickListener);
                card.setOnClickListener(onClickListener);

                chooseLayout.addView(card);
                radioButtons.add(radio);
            }


        }

    }

    private void toggleChecked(){
        String toggledButton="select ans_id from ansvers where ((user=?)and(cont_id=?)and(test_id=?))";

        int cont_id=getIntent().getIntExtra(getString(R.string.contest_pos), -1);
        int test_id=getIntent().getIntExtra(getString(R.string.test_pos),-1);
        Cursor cursor=database.rawQuery(toggledButton,new String[]{preferences.getString("login",""),
                String.valueOf(cont_id),String.valueOf(test_id)});
        if(cursor.getCount()!=0){
            cursor.moveToFirst();
            int toggled=cursor.getInt(0);

            toggleRadioButton(toggled);
        }
        cursor.close();
    }

    private void toggleRadioButton(int n){
        for (int i=0;i<radioButtons.size();i++){
            radioButtons.get(i).setChecked(false);
            if(((int)radioButtons.get(i).getTag())==n)radioButtons.get(i).setChecked(true);
        }

    }

    private int dpToInt(int dp){
        Resources r=this.getResources();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dp,r.getDisplayMetrics());
    }

    private final String ADD_U_ANSVER="insert or replace into ansvers values (?,?,?,?);";
    private View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            toggleRadioButton((int)v.getTag());
            int cont_id=getIntent().getIntExtra(getString(R.string.contest_pos), -1);
            int test_id=getIntent().getIntExtra(getString(R.string.test_pos), -1);
            int ans_id=(int)v.getTag();


            database.execSQL(ADD_U_ANSVER,new String[]{preferences.getString("login",""),
                    String.valueOf(cont_id),String.valueOf(test_id),String.valueOf(ans_id)});

        }
    };
}
