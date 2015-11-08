package eu.honzaik.fyzikaproandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PohybActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pohyb);
        this.overridePendingTransition(R.anim.from_right, R.anim.to_left);


    }
}
