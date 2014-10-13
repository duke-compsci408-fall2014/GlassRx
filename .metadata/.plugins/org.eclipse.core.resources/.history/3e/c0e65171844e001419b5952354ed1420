package com.compsci408.alarms;

import com.compsci408.datatypes.FieldNames;

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
	    intent.putExtra(FieldNames.ID, model.mId);
	    intent.putExtra(FieldNames.NAME, model.mName);
	    intent.putExtra(FieldNames.TIME_HOUR, model.mTimeHour);
	    intent.putExtra(FieldNames.TIME_MINUTE, model.mTimeMinute);
	    intent.putExtra(FieldNames.TONE, model.mAlarmTone);
	 
	    return PendingIntent.getService(context, (int) model.mId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}