package de.netzrac.myhrm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.view.Display;
import android.widget.TextView;

import com.dsi.ant.plugins.antplus.pcc.AntPlusHeartRatePcc;
import com.dsi.ant.plugins.antplus.pcc.MultiDeviceSearch;
import com.dsi.ant.plugins.antplus.pcc.defines.DeviceType;
import com.dsi.ant.plugins.antplus.pcc.defines.RequestAccessResult;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.EnumSet;

import static com.dsi.ant.plugins.antplus.pcc.MultiDeviceSearch.*;

public class SettingsActivity extends AppCompatActivity {

    private MultiDeviceSearch mds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Intent intent=getIntent();
        String message=intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        TextView textView=(TextView) findViewById(R.id.textView);
        textView.setText(message);
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

        SearchCallbacks callbacks=new HrmSearchCallbacks(getApplicationContext());
        mds=new MultiDeviceSearch(context, deviceTypes, callbacks);

    }

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

}
