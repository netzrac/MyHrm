package de.netzrac.myhrm;

import android.content.Context;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dsi.ant.plugins.antplus.pcc.AntPlusHeartRatePcc;
import com.dsi.ant.plugins.antplus.pcc.MultiDeviceSearch;
import com.dsi.ant.plugins.antplus.pcc.defines.RequestAccessResult;
import com.dsi.ant.plugins.antplus.pccbase.AntPluginPcc;

import java.util.List;

class HrmSearchCallbacks implements MultiDeviceSearch.SearchCallbacks {

    private Context context;
//    private final HrmListUpdateCallbacl hluc;
    private SettingsActivity settingsActivity;
/**

    public HrmSearchCallbacks(Context context, HrmListUpdateCallbacl hluc) {
        this.context=context;
        this.hluc=hluc;
    }
**/
    public HrmSearchCallbacks(Context applicationContext, SettingsActivity settingsActivity) {
        this.context=applicationContext;
        this.settingsActivity=settingsActivity;
    }

    @Override
    public void onSearchStarted(MultiDeviceSearch.RssiSupport rssiSupport) {

    }

    @Override
    public void onDeviceFound(com.dsi.ant.plugins.antplus.pccbase.MultiDeviceSearch.MultiDeviceSearchResult multiDeviceSearchResult) {

        String name=multiDeviceSearchResult.getDeviceDisplayName();

        HrmSearchResults.add( multiDeviceSearchResult);

//        settingsActivity.addHrmDevice("dfsdfdsfsf");

      //  settingsActivity.addHrmDevice( multiDeviceSearchResult.getDeviceDisplayName()+";"+multiDeviceSearchResult.getAntDeviceNumber());

        //hluc.onDeviceFound( multiDeviceSearchResult);

    }

    @Override
    public void onSearchStopped(RequestAccessResult requestAccessResult) {

    }

};
