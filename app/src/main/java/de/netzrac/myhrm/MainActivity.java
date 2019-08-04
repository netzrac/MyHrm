/*
 * Copyright (c) 2019 Carsten Pratsch.
 *
 * This file is part of MyHrm.
 *
 * MyHrm is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MyHrm is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with waterRowerService.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 */
package de.netzrac.myhrm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.dsi.ant.plugins.antplus.pcc.AntPlusHeartRatePcc;
import com.dsi.ant.plugins.antplus.pccbase.PccReleaseHandle;

import java.io.IOException;
import java.io.Serializable;

public class MainActivity extends AppCompatActivity implements HrmHelper, Serializable {

    public static HrmReceiver hrmReceiver=null;
    public static PccReleaseHandle<AntPlusHeartRatePcc> hrmReleaseHandle=null;
    public static Client client=null;
    private boolean isStarted;
    private Button startStopButton;

    @Override
    public void sendHeartrate( int heartrate) {
        if( client!=null) {
            client.sendHeartrate(heartrate);
        }
    }

    @Override
    public AppCompatActivity getAppCompatActivity() {
        return this;
    }

    public void sendMessage(View view) {
        Intent intent=new Intent(this, de.netzrac.myhrm.SettingsActivity.class);
        startActivity(intent);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        startStopButton=findViewById( R.id.startStopButton);
        init();
    }

    /**
     * Init client and HRM connections and
     */
    private void init() {

        setIsStarted(true);

        SharedPreferences sp=getApplicationContext().getSharedPreferences(getString(R.string.pref_file), MODE_PRIVATE);
        String host=sp.getString(getString(R.string.pref_host), "localhost");
        int port=Integer.parseInt(sp.getString(getString( R.string.pref_port),"1963"));
        try {
            client = new Client(host, port);
        } catch (IOException e) {
            client = null;
            setIsStarted(false);
        }

        hrmReceiver = new HrmReceiver(this);

        try {
            String hrmString = sp.getString(getString(R.string.pref_hrm), "UNKNOWN");
            MainActivity.hrmReleaseHandle = AntPlusHeartRatePcc.requestAccess(getApplicationContext(),
                    SettingsActivity.getAntDeviceNumber(hrmString),
                    0,                                          // don't use proximityThreshold
                    MainActivity.hrmReceiver,
                    MainActivity.hrmReceiver
            );
        } catch (Exception e) {
            setIsStarted(false);
        }

    }

    private void setIsStarted( boolean isStarted) {
        this.isStarted=isStarted;
        if( isStarted) {
            startStopButton.setText(R.string.STOP);
        } else {
            startStopButton.setText(R.string.START);
        }

    }

    private void close() {
        setIsStarted(false);
        MainActivity.hrmReleaseHandle.close();
        client.setCloseConnection();
        client=null;
    }

    public void toggleStartStop( View view) {
        if( isStarted) {
            close();
        } else {
            init();
        }
    }

}
