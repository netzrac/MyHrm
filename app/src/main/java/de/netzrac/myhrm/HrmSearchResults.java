package de.netzrac.myhrm;

import com.dsi.ant.plugins.antplus.pccbase.MultiDeviceSearch;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class HrmSearchResults implements Iterator {

    private static List<MultiDeviceSearch.MultiDeviceSearchResult> results=new ArrayList<MultiDeviceSearch.MultiDeviceSearchResult>();
    private static Iterator<MultiDeviceSearch.MultiDeviceSearchResult> iter=null;

    public static void add(MultiDeviceSearch.MultiDeviceSearchResult multiDeviceSearchResult) {
        results.add(multiDeviceSearchResult);
    }

    public static void clear() {
        results.clear();
    }

    public HrmSearchResults() {
        iter=results.iterator();
    }

    @Override
    public boolean hasNext() {
        return iter.hasNext();
    }

    @Override
    public MultiDeviceSearch.MultiDeviceSearchResult next() {
        return iter.next();
    }


}
