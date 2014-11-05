package com.compsci408.rxcore.alarms;

import com.compsci408.rxcore.Constants;

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
	    intent.putExtra(Constants.ALARM_ID, model.mId);
	    intent.putExtra(Constants.ALARM_NAME, model.mName);
	    intent.putExtra(Constants.ALARM_TIME_HOUR, model.mTimeHour);
	    intent.putExtra(Constants.ALARM_TIME_MINUTE, model.mTimeMinute);
	    intent.putExtra(Constants.ALARM_TONE, model.mAlarmTone);
	 
	    return PendingIntent.getService(context, (int) model.mId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}