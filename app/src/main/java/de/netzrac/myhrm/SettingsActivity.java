package de.netzrac.myhrm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.dsi.ant.plugins.antplus.pcc.AntPlusHeartRatePcc;
import com.dsi.ant.plugins.antplus.pcc.MultiDeviceSearch;
import com.dsi.ant.plugins.antplus.pcc.defines.DeviceType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;

import static com.dsi.ant.plugins.antplus.pcc.MultiDeviceSearch.SearchCallbacks;

public class SettingsActivity extends AppCompatActivity {

    private MultiDeviceSearch mds;

    private static SharedPreferences sp;
    private SharedPreferences.Editor editor;

   // private HrmListUpdateCallbacl hluc;

    private List<String> listHrms=new ArrayList<>();
    private ArrayAdapter<String> adapter;

    EditText editHost;
    EditText editPort;
    TextView viewHrm;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

   //     Intent intent=getIntent();
   //     String message=intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        Context context=getApplicationContext();
        sp=context.getSharedPreferences(getString(R.string.pref_file), MODE_PRIVATE);

        //
        //TextView textView=(TextView) findViewById(R.id.textView);
        //textView.setText(message);
        //
        //TODO initialize hostname and port
        editHost=findViewById(R.id.editTextHostname);
        editPort=findViewById(R.id.editTextPort);

        String prefHost=sp.getString(getString(R.string.pref_host), "localhost");
        String prefPort=sp.getString(getString(R.string.pref_port), "1963");

        editHost.setText(prefHost);
        editHost.setSelectAllOnFocus(true);
        editPort.setText(prefPort);
        editPort.setSelectAllOnFocus(true);

        // initialize device list w/ last device
        viewHrm=findViewById(R.id.selectedHrm);
        viewHrm.setText(sp.getString(getString(R.string.pref_hrm), ""));

        viewHrm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.clear();
                HrmSearchResults hsr=new HrmSearchResults();
                while( hsr.hasNext()) {
                    com.dsi.ant.plugins.antplus.pccbase.MultiDeviceSearch.MultiDeviceSearchResult mdsr = hsr.next();
                    adapter.add(mdsr.getDeviceDisplayName()+";"+mdsr.getAntDeviceNumber());
                }

            }
        });

        listView=findViewById(R.id.listViewHrms);
        adapter=new ArrayAdapter<String>( this, R.layout.list_item, R.id.itemName, listHrms);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                                viewHrm.setText(listHrms.get(i));
                                            }
                                        }
        );

        // Start device search
        searchDevices();

    }

    @Override
    protected void onDestroy() {
        editor=sp.edit();
        String keyPort=getString(R.string.pref_port);
        String valuePort=editPort.getText().toString();
        editor.putString(keyPort,valuePort);
        String keyHost=getString(R.string.pref_host);
        String valueHost=editHost.getText().toString();
        editor.putString(keyHost,valueHost);
        String keyHrm=getString(R.string.pref_hrm);
        String valueHrm=viewHrm.getText().toString();
        editor.putString(keyHrm,valueHrm);
        editor.commit();
        mds.close();

        // reconnect client
        try {
            MainActivity.client=new Client( valueHost, Integer.parseInt(valuePort));
        } catch (IOException e) {
            MainActivity.client=null;
        }

        // reconnect HRM
        connectHrm(valueHrm);

        super.onDestroy();
    }

    private void searchDevices() {

        Context context=getApplicationContext();

        EnumSet<DeviceType> deviceTypes = EnumSet.noneOf(com.dsi.ant.plugins.antplus.pcc.defines.DeviceType.class) ;
        deviceTypes.add(DeviceType.HEARTRATE);

        SearchCallbacks callbacks=new HrmSearchCallbacks(getApplicationContext(), this);
        HrmSearchResults.clear();
        mds=new MultiDeviceSearch(context, deviceTypes, callbacks);

    }

    /**
     *
     * @param hrmDeviceString  format 'name;id'
     */
    public void addHrmDevice( String hrmDeviceString) {
        adapter.add(hrmDeviceString);
    }

    // call connectHrm when leaving settings
    private void connectHrm( String hrmString) {

        if (MainActivity.hrmReleaseHandle != null) {
            MainActivity.hrmReleaseHandle.close();
        }

        MainActivity.hrmReleaseHandle = AntPlusHeartRatePcc.requestAccess(getApplicationContext(),
                MainActivity.getAntDeviceNumber(hrmString),
                0,                                          // don't use proximityThreshold
                MainActivity.hrmReceiver,
                MainActivity.hrmReceiver
        );

    }
/**
    //TODO connect with last connected HRM
    private static HrmReceiver connectConfiguredHrm() {

        return null;
    }

    //TODO connect with last connected client
    private static Client connectConfiguredServer() {
        String prefHost=sp.getString(getString(R.string.pref_host), "localhost");
        String prefPort=sp.getString(getString(R.string.pref_port), "1963");

        return null;
    }
**/
}
