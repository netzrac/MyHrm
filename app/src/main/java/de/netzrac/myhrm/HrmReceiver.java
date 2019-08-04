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

import android.widget.TextView;

import com.dsi.ant.plugins.antplus.pcc.AntPlusHeartRatePcc;
import com.dsi.ant.plugins.antplus.pcc.defines.DeviceState;
import com.dsi.ant.plugins.antplus.pcc.defines.EventFlag;
import com.dsi.ant.plugins.antplus.pcc.defines.RequestAccessResult;
import com.dsi.ant.plugins.antplus.pccbase.AntPluginPcc;
import com.dsi.ant.plugins.antplus.pccbase.AntPluginPcc.IPluginAccessResultReceiver;

import java.math.BigDecimal;
import java.util.EnumSet;

public class HrmReceiver implements IPluginAccessResultReceiver<AntPlusHeartRatePcc>,
        AntPluginPcc.IDeviceStateChangeReceiver, AntPlusHeartRatePcc.IHeartRateDataReceiver {

    HrmHelper hrmHelper=null;

    public HrmReceiver(HrmHelper activity) {
        this.hrmHelper=activity;
    }

    @Override
    public void onResultReceived(AntPlusHeartRatePcc antPlusHeartRatePcc, RequestAccessResult requestAccessResult, DeviceState deviceState) {

        if( antPlusHeartRatePcc!=null) {
            antPlusHeartRatePcc.subscribeHeartRateDataEvent(this);
        }
    }

    @Override
    public void onDeviceStateChange(DeviceState deviceState) {

    }

    @Override
    public void onNewHeartRateData(long timestamp, EnumSet<EventFlag> enumSet, int heartrate, long l1, BigDecimal bigDecimal, AntPlusHeartRatePcc.DataState dataState) {

        TextView textView=(TextView) hrmHelper.getAppCompatActivity().findViewById(R.id.heartRate);
        textView.setText(String.valueOf(timestamp)+":"+String.valueOf(heartrate)+":"+String.valueOf(l1));
        hrmHelper.sendHeartrate( heartrate);

    }
}
