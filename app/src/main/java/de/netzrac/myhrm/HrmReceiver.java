package de.netzrac.myhrm;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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

        antPlusHeartRatePcc.subscribeHeartRateDataEvent(this);

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
