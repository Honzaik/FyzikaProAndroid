package eu.honzaik.fyzikaproandroid;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class PohybActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pohyb);
        this.overridePendingTransition(R.anim.from_right, R.anim.to_left);

        Toolbar pohybToolbar = (Toolbar) findViewById(R.id.pohyb_activity_toolbar);
        setSupportActionBar(pohybToolbar);
        ActionBar ab = getSupportActionBar();
        if(ab != null) ab.setDisplayHomeAsUpEnabled(true);


    }
}
