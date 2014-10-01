package com.compsci408.androidrx.provider;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import com.compsci408.androidrx.R;

public class NewTimeActivity extends Activity {

	private Button addTimeComplete;
	private TimePicker tp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_time);
		tp = (TimePicker)findViewById(R.id.timePicker1);
		
	}
		
	private NotificationCompat.Builder mBuilder;
	private int mNotificationId = 001;

	public void addTimeComplete(View view){
		

		mBuilder =
		        new NotificationCompat.Builder(this)
		        .setSmallIcon(R.drawable.custom_button_default)
		        .setContentTitle("Pill Reminder")
		        .setContentText("Time to take your Teamocil!");
		
		Intent resultIntent = new Intent(this, PatientActivity.class);
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		stackBuilder.addParentStack(PatientActivity.class);
		stackBuilder.addNextIntent(resultIntent);

		PendingIntent resultPendingIntent =
			    PendingIntent.getActivity(
			    this,
			    0,
			    resultIntent,
			    PendingIntent.FLAG_UPDATE_CURRENT
			);
		
    	mBuilder.setContentIntent(resultPendingIntent);

		
		BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override public void onReceive( Context context, Intent _ )
            {
                NotificationManager mNotifyMgr = 
                        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                mNotifyMgr.notify(mNotificationId, mBuilder.build());
                   }
        };
        
        
        this.registerReceiver( receiver, new IntentFilter("com.blah.blah.somemessage") );

        PendingIntent pintent = PendingIntent.getBroadcast( this, 0, new Intent("com.blah.blah.somemessage"), 0 );
        AlarmManager manager = (AlarmManager)(this.getSystemService( Context.ALARM_SERVICE ));

        // set alarm to fire 5 sec (1000*5) from now (SystemClock.elapsedRealtime())
        manager.set( AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + 5000, pintent );
        Toast.makeText(this, "Alarm set!", Toast.LENGTH_SHORT).show();

			
		
		
		
		Intent intent = new Intent(NewTimeActivity.this, NewMedActivity.class);
		startActivity(intent);
//		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//		finish();
		}
		
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_time, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
