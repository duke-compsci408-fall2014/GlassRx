package com.compsci408.rxcore.alarms;

import com.compsci408.rxcore.datatypes.Constants;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmManagerHelper extends BroadcastReceiver {
	
    @Override
    public void onReceive(Context context, Intent intent) {
        setAlarms(context);
    }
 
    public static void setAlarms(Context context) {
 
    }
 
    public static void cancelAlarms(Context context) {
 
    }
 
    private static PendingIntent createPendingIntent(Context context, Alarm model) {
	    Intent intent = new Intent(context, AlarmService.class);
	    intent.putExtra(Constants.ID, model.mId);
	    intent.putExtra(Constants.NAME, model.mName);
	    intent.putExtra(Constants.TIME_HOUR, model.mTimeHour);
	    intent.putExtra(Constants.TIME_MINUTE, model.mTimeMinute);
	    intent.putExtra(Constants.TONE, model.mAlarmTone);
	 
	    return PendingIntent.getService(context, (int) model.mId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}