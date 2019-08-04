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

import android.content.Context;

import com.dsi.ant.plugins.antplus.pcc.MultiDeviceSearch;
import com.dsi.ant.plugins.antplus.pcc.defines.RequestAccessResult;

class HrmSearchCallbacks implements MultiDeviceSearch.SearchCallbacks {

    private Context context;

    public HrmSearchCallbacks(Context applicationContext) {
        this.context=applicationContext;
    }

    @Override
    public void onSearchStarted(MultiDeviceSearch.RssiSupport rssiSupport) {
    }

    @Override
    public void onDeviceFound(com.dsi.ant.plugins.antplus.pccbase.MultiDeviceSearch.MultiDeviceSearchResult multiDeviceSearchResult) {
        String name=multiDeviceSearchResult.getDeviceDisplayName();
        HrmSearchResults.add( multiDeviceSearchResult);
    }

    @Override
    public void onSearchStopped(RequestAccessResult requestAccessResult) {
    }

};
