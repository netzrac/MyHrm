package de.netzrac.myhrm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.dsi.ant.plugins.antplus.pcc.AntPlusHeartRatePcc;
import com.dsi.ant.plugins.antplus.pcc.MultiDeviceSearch;
import com.dsi.ant.plugins.antplus.pcc.defines.DeviceType;

import java.util.EnumSet;

import static com.dsi.ant.plugins.antplus.pcc.MultiDeviceSearch.SearchCallbacks;

public class SettingsActivity extends AppCompatActivity {

    private MultiDeviceSearch mds;

    HrmListUpdateCallbacl hluc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        hluc=new HrmListUpdateCallbacl() {
            @Override
            public void onDeviceFound(com.dsi.ant.plugins.antplus.pccbase.MultiDeviceSearch.MultiDeviceSearchResult multiDeviceSearchResult) {
                //TODO add found device to device list
            }
        };


        Intent intent=getIntent();
        String message=intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        //
        //TextView textView=(TextView) findViewById(R.id.textView);
        //textView.setText(message);
        //
        //TODO initialize device list w/ last device

        //TODO Start device search in the case START button pressed
        searchDevices();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mds.close();
    }

    private void searchDevices() {

        Context context=getApplicationContext();

        EnumSet<DeviceType> deviceTypes = EnumSet.noneOf(com.dsi.ant.plugins.antplus.pcc.defines.DeviceType.class) ;
        deviceTypes.add(DeviceType.HEARTRATE);

        SearchCallbacks callbacks=new HrmSearchCallbacks(getApplicationContext(), hluc);
        mds=new MultiDeviceSearch(context, deviceTypes, callbacks);

    }

    //TODO call connectHrm when leaving settings
    private void connectHrm( com.dsi.ant.plugins.antplus.pccbase.MultiDeviceSearch.MultiDeviceSearchResult multiDeviceSearchResult) {

        if (MainActivity.hrmReleaseHandle != null) {
            MainActivity.hrmReleaseHandle.close();
        }

        MainActivity.hrmReleaseHandle = AntPlusHeartRatePcc.requestAccess(getApplicationContext(),
                multiDeviceSearchResult.getAntDeviceNumber(),
                0,                                          // don't use proximityThreshold
                MainActivity.hrmReceiver,
                MainActivity.hrmReceiver
        );

    }

    //TODO connect with last connected HRM
    private static HrmReceiver connectConfiguredHrm() {

        return null;
    }

    //TODO connect with last connected HRM
    private static Client connectConfiguredServer() {

        return null;
    }

}
