package eu.honzaik.fyzikaproandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class PohybyActivity extends AppCompatActivity{

    private Button buttonRovnomerny;
    private Button buttonZrychleny;
    private Button buttonZpomaleny;

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
    }
}
