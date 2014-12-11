package com.compsci408.rxcore.requests;

import com.compsci408.rxcore.Constants;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;

/**
 * {@link BroadcastReceiver} for handling intermittent server polling
 * @author Evan
 */
public class PollReceiver extends BroadcastReceiver {

	@Override
    public void onReceive(Context context, Intent intent) {   
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
        wl.acquire();

        //TODO:  Implement polling logic

        wl.release();
    }

	/**
	 * Set an alarm, at which time the server will be polled
	 */
	public void SetAlarm(Context context) {
	    AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
	    Intent i = new Intent(context, PollReceiver.class);
	    PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
	    am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), Constants.POLLING_INTERVAL, pi); // Millisec * Second * Minute
	}
	
	/**
	 * Cancel an alarm
	 */
	public void CancelAlarm(Context context) {
	    Intent intent = new Intent(context, PollReceiver.class);
	    PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
	    AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
	    alarmManager.cancel(sender);
	}

}
