package de.netzrac.myhrm;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import com.dsi.ant.plugins.antplus.pcc.AntPlusHeartRatePcc;
import com.dsi.ant.plugins.antplus.pcc.MultiDeviceSearch;
import com.dsi.ant.plugins.antplus.pcc.defines.RequestAccessResult;
import com.dsi.ant.plugins.antplus.pccbase.AntPluginPcc;

class HrmSearchCallbacks implements MultiDeviceSearch.SearchCallbacks {

    private final Context context;
    private final HrmSearchResults hsr;

    public HrmSearchCallbacks(Context context) {
        this.context=context;
        this.hsr=new HrmSearchResults();
    }

    @Override
    public void onSearchStarted(MultiDeviceSearch.RssiSupport rssiSupport) {

    }

    @Override
    public void onDeviceFound(com.dsi.ant.plugins.antplus.pccbase.MultiDeviceSearch.MultiDeviceSearchResult multiDeviceSearchResult) {

        String name=multiDeviceSearchResult.getDeviceDisplayName();

        hsr.add( multiDeviceSearchResult);

    }

    @Override
    public void onSearchStopped(RequestAccessResult requestAccessResult) {

    }

};
