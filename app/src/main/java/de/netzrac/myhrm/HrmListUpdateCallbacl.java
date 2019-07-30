package de.netzrac.myhrm;

import com.dsi.ant.plugins.antplus.pccbase.MultiDeviceSearch;

interface HrmListUpdateCallbacl {
    void onDeviceFound(MultiDeviceSearch.MultiDeviceSearchResult multiDeviceSearchResult);
}
