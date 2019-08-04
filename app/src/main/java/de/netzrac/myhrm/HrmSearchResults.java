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
