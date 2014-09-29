package com.compsci408.alarms;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmManagerHelper extends BroadcastReceiver {
 
	public static final String ID = "id";
	public static final String NAME = "name";
	public static final String TIME_HOUR = "timeHour";
	public static final String TIME_MINUTE = "timeMinute";
	public static final String TONE = "alarmTone";
	
    @Override
    public void onReceive(Context context, Intent intent) {
        setAlarms(context);
    }
 
    public static void setAlarms(Context context) {
 
    }
 
    public static void cancelAlarms(Context context) {
 
    }
 
    private static PendingIntent createPendingIntent(Context context, AlarmModel model) {
	    Intent intent = new Intent(context, AlarmService.class);
	    intent.putExtra(ID, model.id);
	    intent.putExtra(NAME, model.name);
	    intent.putExtra(TIME_HOUR, model.timeHour);
	    intent.putExtra(TIME_MINUTE, model.timeMinute);
	    intent.putExtra(TONE, model.alarmTone);
	 
	    return PendingIntent.getService(context, (int) model.id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}