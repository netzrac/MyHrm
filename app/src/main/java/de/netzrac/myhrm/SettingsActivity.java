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

import android.annotation.SuppressLint;
import android.content.Context;
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
import java.util.List;
import java.util.StringTokenizer;

import static com.dsi.ant.plugins.antplus.pcc.MultiDeviceSearch.SearchCallbacks;

public class SettingsActivity extends AppCompatActivity {

    private MultiDeviceSearch mds;

    private static SharedPreferences sp;
    private SharedPreferences.Editor editor;

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

        Context context=getApplicationContext();
        sp=context.getSharedPreferences(getString(R.string.pref_file), MODE_PRIVATE);

        // initialize hostname and port
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
        viewHrm.setText(sp.getString(getString(R.string.pref_hrm), getString( R.string.selectedHrmDevice)));

        listView=findViewById(R.id.listViewHrms);
        adapter=new ArrayAdapter<String>( this, R.layout.list_item, R.id.itemName, listHrms);
        listView.setAdapter(adapter);

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

    @SuppressLint("ApplySharedPref")
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
        super.onDestroy();
    }

    private void searchDevices() {

        Context context=getApplicationContext();

        EnumSet<DeviceType> deviceTypes = EnumSet.noneOf(com.dsi.ant.plugins.antplus.pcc.defines.DeviceType.class) ;
        deviceTypes.add(DeviceType.HEARTRATE);

        SearchCallbacks callbacks=new HrmSearchCallbacks(getApplicationContext());
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

    /**
     *
     * @param hrmString format 'name;id'
     * @return device id
     * @throws Exception if no device number defined
     */
    public static int getAntDeviceNumber( String hrmString) throws Exception {
        StringTokenizer st=new StringTokenizer( hrmString, ";");
        String token=st.nextToken();
        if( st.hasMoreTokens()) {
            return Integer.parseInt(st.nextToken());
        }
        throw new Exception( "Invalid device number.");
    }

}
