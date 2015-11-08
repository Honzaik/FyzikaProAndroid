package eu.honzaik.fyzikaproandroid;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import eu.honzaik.fyzikaproandroid.other.ExpandableListAdapter;

public class PrevodActivity extends AppCompatActivity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> headerTitles = new ArrayList<String>();
    HashMap<String, Integer> prevodniciList = new HashMap<String, Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prevody);
        this.overridePendingTransition(R.anim.from_right, R.anim.to_left);

        Toolbar prevodyToolbar = (Toolbar) findViewById(R.id.prevody_activity_toolbar);
        setSupportActionBar(prevodyToolbar);

        ActionBar ab = getSupportActionBar();
        if(ab != null) ab.setDisplayHomeAsUpEnabled(true);

        prepareData();
        expListView = (ExpandableListView) findViewById(R.id.prevody_elv);
        listAdapter = new ExpandableListAdapter(this, headerTitles, prevodniciList);
        expListView.setAdapter(listAdapter);
    }

    private void prepareData() {
        headerTitles.add("Čas");
        headerTitles.add("Délka");
        headerTitles.add("Objem");
        headerTitles.add("Obsah");
        headerTitles.add("Práce a energie");
        headerTitles.add("Rychlost");
        headerTitles.add("Teplota");
        headerTitles.add("Tlak");
        headerTitles.add("Výkon");

        prevodniciList.put(headerTitles.get(0), 0);
        prevodniciList.put(headerTitles.get(1), 1);
        prevodniciList.put(headerTitles.get(2), 2);
        prevodniciList.put(headerTitles.get(3), 3);
        prevodniciList.put(headerTitles.get(4), 4);
        prevodniciList.put(headerTitles.get(5), 5);
        prevodniciList.put(headerTitles.get(6), 6);
        prevodniciList.put(headerTitles.get(7), 7);
        prevodniciList.put(headerTitles.get(8), 8);
    }
}
