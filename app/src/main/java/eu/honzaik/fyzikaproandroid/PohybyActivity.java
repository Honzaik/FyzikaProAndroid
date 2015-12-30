package eu.honzaik.fyzikaproandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import eu.honzaik.fyzikaproandroid.other.RovnomernyPohyb;

public class PohybyActivity extends AppCompatActivity{

    private Button buttonRovnomerny;
    private Button buttonZrychleny;
    private Button buttonZpomaleny;
    private Button buttonSvislyVzhuru;
    private Button buttonVodorovny;
    private Button buttonSikmyVzhuru;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pohyby);
        this.overridePendingTransition(R.anim.from_right, R.anim.to_left);

        Toolbar pohybyToolbar = (Toolbar) findViewById(R.id.main_activity_toolbar);
        setSupportActionBar(pohybyToolbar);

        ActionBar ab = getSupportActionBar();
        if(ab != null) ab.setDisplayHomeAsUpEnabled(true);


        buttonRovnomerny = (Button) findViewById(R.id.pohyby_activity_button_rovnomerny);
        buttonZrychleny = (Button) findViewById(R.id.pohyby_activity_button_zrychleny);
        buttonZpomaleny = (Button) findViewById(R.id.pohyby_activity_button_zpomaleny);
        buttonSvislyVzhuru = (Button) findViewById(R.id.pohyby_activity_button_svisly_vzhuru);
        buttonVodorovny = (Button) findViewById(R.id.pohyby_activity_button_vodorovny);
        buttonSikmyVzhuru = (Button) findViewById(R.id.pohyby_activity_button_sikmy_vzhuru);


        buttonRovnomerny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PohybyActivity.this, RovnomernyPohybActivity.class);
                startActivity(intent);
            }
        });

        buttonZrychleny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PohybyActivity.this, ZrychlenyPohybActivity.class);
                startActivity(intent);
            }
        });

        buttonZpomaleny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PohybyActivity.this, ZpomalenyPohybActivity.class);
                startActivity(intent);
            }
        });

        buttonSvislyVzhuru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PohybyActivity.this, SvislyVzhuruVrhActivity.class);
                startActivity(intent);
            }
        });

        buttonVodorovny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PohybyActivity.this, VodorovnyVrhActivity.class);
                startActivity(intent);
            }
        });

        buttonSikmyVzhuru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PohybyActivity.this, SikmyVzhuruVrhActivity.class);
                startActivity(intent);
            }
        });

    }
}
