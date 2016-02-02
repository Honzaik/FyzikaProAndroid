package eu.honzaik.fyzikaproandroid;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "FYS";
    private Button menuButton1;
    private Button menuButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.overridePendingTransition(R.anim.from_left, R.anim.to_right);

        Toolbar mainToolbar = (Toolbar) findViewById(R.id.main_activity_toolbar);
        setSupportActionBar(mainToolbar);

        menuButton1 = (Button) findViewById(R.id.main_activity_menu_button_first);
        menuButton2 = (Button) findViewById(R.id.main_activity_menu_button_second);

        menuButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PrevodActivity.class);
                startActivity(intent);
            }
        });

        menuButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PohybyActivity.class);
                startActivity(intent);
            }
        });

        mainToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebView view = (WebView) LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_licenses, null);
                view.loadUrl("file:///android_asset/licenses.html");
                AlertDialog dialog =new AlertDialog.Builder(MainActivity.this, R.style.AppTheme).setTitle("About").setView(view).setPositiveButton(android.R.string.ok, null).show();
            }
        });
    }
}
