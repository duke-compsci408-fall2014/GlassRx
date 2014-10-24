package com.compsci408.androidrx.provider;

import java.util.Calendar;
import java.util.Date;

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
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TimePicker;
import android.widget.Toast;

import com.compsci408.androidrx.R;
import com.compsci408.rxcore.alarms.AlarmsDataSource;
import com.compsci408.rxcore.alarms.AlarmReceiver;
import com.compsci408.rxcore.datatypes.Day;

public class NewTimeActivity extends Activity {

	private Button addTimeComplete;
	private TimePicker tp;
	private NotificationCompat.Builder mBuilder;
	private int mNotificationId = 001;
	private String mMedName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_time);
		tp = (TimePicker)findViewById(R.id.med_time_picker);
		addTimeComplete = (Button) findViewById(R.id.add_med_complete);
		
		addTimeComplete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlarmsDataSource ds = new AlarmsDataSource(NewTimeActivity.this);
				CheckBox[] days = {(CheckBox) NewTimeActivity.this.findViewById(R.id.checkBox1),
						(CheckBox) NewTimeActivity.this.findViewById(R.id.checkBox2),
						(CheckBox) NewTimeActivity.this.findViewById(R.id.checkBox3),
						(CheckBox) NewTimeActivity.this.findViewById(R.id.checkBox4),
						(CheckBox) NewTimeActivity.this.findViewById(R.id.checkBox5),
						(CheckBox) NewTimeActivity.this.findViewById(R.id.checkBox6),
						(CheckBox) NewTimeActivity.this.findViewById(R.id.checkBox7) };
				for (int i = 0; i < days.length; i++) {
					if (days[i].isChecked()) {
						ds.createAlarm(tp.getCurrentHour(), tp.getCurrentMinute(), i, Day.values()[i].getName());
						setAlarm(tp.getCurrentHour(), tp.getCurrentMinute());
					}
				}
				Intent intent = new Intent(NewTimeActivity.this, EditMedActivity.class);
				intent.putExtra("medName", mMedName);
				startActivity(intent);
				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
				finish();
			}
			
		});
		
		Intent intent = getIntent();
		mMedName = intent.getStringExtra("NewMed");
		
	}
	
	private void setAlarm(int hour, int min) {
		
		Date dat  = new Date();//initializes to now
	    Calendar cal_alarm = Calendar.getInstance();
	    Calendar cal_now = Calendar.getInstance();
	    cal_now.setTime(dat);
	    cal_alarm.setTime(dat);
	    cal_alarm.set(Calendar.HOUR_OF_DAY,hour);//set the alarm time
	    cal_alarm.set(Calendar.MINUTE, min);
	    cal_alarm.set(Calendar.SECOND,0);
	    if(cal_alarm.before(cal_now)){//if its in the past increment
	        cal_alarm.add(Calendar.DATE,1);
	    }
	    Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
	    PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), 1, intent, 0);
	    AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
	    alarmManager.set(AlarmManager.RTC_WAKEUP, cal_alarm.getTimeInMillis(), pendingIntent);
	}

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

			
		
		
		
		Intent intent = new Intent(NewTimeActivity.this, EditMedActivity.class);
		startActivity(intent);
//		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//		finish();
		}
		
	
	@Override
	public void onBackPressed() {
//		AlarmsDataSource ds = new AlarmsDataSource(NewTimeActivity.this);
//		ds.open();
//		CheckBox[] days = {(CheckBox) NewTimeActivity.this.findViewById(R.id.checkBox1),
//				(CheckBox) NewTimeActivity.this.findViewById(R.id.checkBox2),
//				(CheckBox) NewTimeActivity.this.findViewById(R.id.checkBox3),
//				(CheckBox) NewTimeActivity.this.findViewById(R.id.checkBox4),
//				(CheckBox) NewTimeActivity.this.findViewById(R.id.checkBox5),
//				(CheckBox) NewTimeActivity.this.findViewById(R.id.checkBox6),
//				(CheckBox) NewTimeActivity.this.findViewById(R.id.checkBox7) };
//		for (int i = 0; i < days.length; i++) {
//			if (days[i].isChecked()) {
//				ds.createAlarm(tp.getCurrentHour(), tp.getCurrentMinute(), i, Day.values()[i].getName());
//				setAlarm(tp.getCurrentHour(), tp.getCurrentMinute());
//			}
//		}
//		ds.close();
		Intent intent = new Intent(NewTimeActivity.this, EditMedActivity.class);
		intent.putExtra("medName", mMedName);
		startActivity(intent);
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
		finish();
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