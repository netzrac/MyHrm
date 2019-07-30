package de.netzrac.myhrm;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.dsi.ant.plugins.antplus.pcc.AntPlusHeartRatePcc;
import com.dsi.ant.plugins.antplus.pccbase.PccReleaseHandle;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements HrmHelper {

    public static HrmReceiver hrmReceiver=null;
    public static PccReleaseHandle<AntPlusHeartRatePcc> hrmReleaseHandle=null;

    //TextView mTextMessage;
    public static final String EXTRA_MESSAGE="de.netzrac.myhrm.MESSAGE";

    private String host="filamenti";
  //  private String host="cpmiix";
    private int port=1963;
    private Client client;

    @Override
    public void sendHeartrate( int heartrate) {
        if( client!=null) {
            client.sendHeartrate(heartrate);
        }
    }

    @Override
    public AppCompatActivity getAppCompatActivity() {
        return (AppCompatActivity) this;
    }

    public void sendMessage(View view) {
        Intent intent=new Intent(this, de.netzrac.myhrm.SettingsActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        String message=editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    //mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    //mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };
  //  private SettingsActivity settings;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            client=new Client( host, port);
        } catch (IOException e) {
            client=null;
        }
        hrmReceiver=new HrmReceiver(this);

        //BottomNavigationView navView = findViewById(R.id.nav_view);
        //mTextMessage = findViewById(R.id.message);
        //navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
