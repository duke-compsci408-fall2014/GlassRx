package com.compsci408.glassrx.glassrx2014.patient;


import android.app.Activity;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.RemoteViews;

import com.compsci408.glassrx.glassrx2014.R;
import com.compsci408.glassrx.glassrx2014.rxcore.Controller;
import com.compsci408.glassrx.glassrx2014.rxcore.datatypes.Medication;
import com.compsci408.glassrx.glassrx2014.rxcore.datatypes.Schedule;
import com.compsci408.glassrx.glassrx2014.rxcore.listeners.OnScheduleLoadedListener;
import com.google.android.glass.timeline.LiveCard;
import com.google.android.glass.timeline.LiveCard.PublishMode;
import com.google.android.glass.view.WindowUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A {@link Service} that publishes a {@link LiveCard} in the timeline.
 */
public class NextMedLiveCard extends Service {

    private static final long GLASS_WRONG_OFFSET = 1182241714 + 81700000;
    private static final String LIVE_CARD_TAG = "NextMedLiveCard";
    private static final int MAX_STREAMS = 1;

    private LiveCard mLiveCard;
    private boolean mRunning = true;
    private long DELAY_MILLIS = 5000;
    private final Handler mHandler = new Handler();

    private static final int SOUND_PRIORITY = 1000;
    private SoundPool mSoundPool;
    private int mTimerFinishedSoundId = 1;
    int count = 0;

    private Controller mController;
    public ArrayList<Schedule> medList = new ArrayList<Schedule>();

    private Timer heartbeat;

    private RemoteViews remoteViews;

//    private final Runnable mUpdateTextRunnable = new Runnable() {
//        @Override
//        public void run() {
////            if(mRunning) {
//
////                postDelayed(mUpdateTextRunnable, DELAY_MILLIS);
//                updateText("It's time to take your medicine:\nName: Aricept\nTime: 7pm\nDosage: 100mg" + count);
////                mRunning = false;
//                count++;
////            }
//
//        }
//    };

//    public void nextAlarm(String cardText){
//        remoteViews = new RemoteViews(getPackageName(), R.layout.next_med_live_card);
//        remoteViews.setCharSequence(R.id.text_view, "setText", cardText);
//        mLiveCard.setViews(remoteViews);
//
//        Intent menuIntent = new Intent(this, LiveCardMenuActivity.class);
//        mLiveCard.setAction(PendingIntent.getActivity(this, 0, menuIntent, 0));
//
//
//        setAlarm();
//
//    }
    
    public boolean flag = true;
    public boolean flag2 = true;



    public void updateText(String cardText, boolean alarm){
        if(alarm) {
//            mLiveCard.unpublish();
        }
        remoteViews = new RemoteViews(getPackageName(), R.layout.next_med_live_card);
        remoteViews.setCharSequence(R.id.text_view, "setText", cardText);
        mLiveCard.setViews(remoteViews);


        Intent menuIntent = new Intent(this, LiveCardMenuActivity.class);
        mLiveCard.setAction(PendingIntent.getActivity(this, 0, menuIntent, 0));


        if(alarm) {
            playSound();
//            mLiveCard.publish(PublishMode.REVEAL);
            mLiveCard.navigate();

        }

//        if(flag) {
//            flag = false;
//        }


    }

    public boolean postDelayed(Runnable action, long delayMillis) {
        return mHandler.postDelayed(action, delayMillis);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {



        mController = Controller.getInstance(this);

        mController.getPatientSchedule(new OnScheduleLoadedListener() {

            @Override
            public void onScheduleLoaded(List<Schedule> schedule) {
                Set<Schedule> currentMeds = new HashSet<Schedule>();
                Schedule med;
                for (int i = 0; i < schedule.size(); i++) {
//                    Log.e("Schedule: ", schedule.get(i).getDay_to_take()+ " " + schedule.get(i).getTime_to_take());

                    med = schedule.get(i);
                    currentMeds.add(med);
                    if(!medList.contains(med)) {
                        medList.add(med);
                    }
                }


//                mCurrentAdapter = new ArrayAdapter<String>(
//                        PatientMedListActivity.this,
//                        android.R.layout.simple_list_item_1,
//                        new ArrayList<String>(currentMeds));
//                listView.setAdapter(mCurrentAdapter);
                mController.showProgress(false);

                if(mLiveCard == null) mLiveCard = new LiveCard(NextMedLiveCard.this, LIVE_CARD_TAG);

                remoteViews = new RemoteViews(getPackageName(), R.layout.next_med_live_card);


                String setText = "Next Medication: " + medList.get(0).getMedication();

                setText += "  "+ medList.get(0).getDay_to_take();
                remoteViews.setCharSequence(R.id.text_view, "setText", setText);

                mLiveCard.setViews(remoteViews);

                // Display the options menu when the live card is tapped.
                Intent menuIntent = new Intent(NextMedLiveCard.this, LiveCardMenuActivity.class);
                mLiveCard.setAction(PendingIntent.getActivity(NextMedLiveCard.this, 0, menuIntent, 0));
                mLiveCard.publish(PublishMode.REVEAL);

                setAlarm();

            }

        });



        mSoundPool = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0);
        mTimerFinishedSoundId = mSoundPool.load(this, R.raw.timer_finished, SOUND_PRIORITY);

        if(heartbeat == null) {
            heartbeat = new Timer();
        }




        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if (mLiveCard != null && mLiveCard.isPublished()) {
            mLiveCard.unpublish();
            mLiveCard = null;
        }
        super.onDestroy();
    }


    protected void playSound() {
        mSoundPool.play(mTimerFinishedSoundId,
                1 /* leftVolume */,
                1 /* rightVolume */,
                SOUND_PRIORITY,
                2 /* loop */,
                1 /* rate */);
    }
    int count2 = 0;


    private void setAlarm()
    {
        //get stringToSet and dateToTake from controller

        final Handler handler = new Handler();

        long timeUntilAlarm = 1000000;
        Schedule s = findNextMed();
        if(s == null){
            updateText("No scheduled medications", false);
            return;
        }

        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = fmt.parse("2014-12-09");
        } catch (ParseException e) {
            e.printStackTrace();
        }

//        timeUntilAlarm = getDateFromSchedule(s).getTime() - System.currentTimeMillis() - GLASS_WRONG_OFFSET;
        timeUntilAlarm = date.getTime() + 30*60000 - System.currentTimeMillis() - GLASS_WRONG_OFFSET;
//        timeUntilAlarm = 8000;
        Log.e("Time until alarm", "" + timeUntilAlarm);

        mController.setMedName(s.getMedication());
        final String stringToSet = "Medication name: " + s.getMedication() + "\n" + "When: " + s.getDay_to_take() +
                " at " + s.getTime_to_take();
        updateText(stringToSet, false);

        final TimerTask nextMedTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            updateText("Next med: " + count2, false);
                            count2++;
                        } catch (Exception e) {

                        }
                        finally{
                            Log.e("this happened", "true" + count2);
                            setAlarm();
                        }
                    }
                });
            }
        };

        TimerTask confirmMedTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            updateText("It's time to take: " + count2, true);
                            count2++;
                        } catch (Exception e) {

                        }
                        finally{
                            heartbeat.schedule(nextMedTask, 10000);

                            Log.e("this happened", "true" + count2);
                        }
                    }
                });
            }
        };

        String takeMedText = "";

        heartbeat.schedule(confirmMedTask, timeUntilAlarm);



//
//
//
//        long offset = 1182241714;
//
//        Log.e("Date date millis: ", ""+ date.getTime());
//
//        Log.e("Now millis: ", ""+ System.currentTimeMillis());
//        long delay = 15000;
//        heartbeat.
//        Log.d("offset: ", Integer.toString(dateToTake.getTimezoneOffset()));

//        setAlarm();
    }


    private Schedule findNextMed(){
        if(medList == null) return null;

        Date soonest = null;
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            soonest = fmt.parse("2030-01-01");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        boolean changed = false;
        Date todayDate = new Date(System.currentTimeMillis() + GLASS_WRONG_OFFSET);
        Date testDate;
        Schedule soonestSched = null;
        long timeOffset = 0;
        String timeString;
        for(Schedule s : medList){
            testDate = null;

            try {
                testDate = fmt.parse(s.getDay_to_take());
                timeString = s.getTime_to_take();
                timeOffset = Long.parseLong(timeString.split(":")[0])*3600000 + Long.parseLong(timeString.split(":")[1])*60000;
                testDate = new Date(testDate.getTime() + timeOffset);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if(testDate == null) return null;


            if(testDate.after(todayDate) && testDate.after(soonest)){
//            if(testDate.before(soonest)){
                soonest = testDate;
                soonestSched = s;
                changed = true;
            }

        }

        if(changed){
            return soonestSched;
        }

        return null;

    }

    public Date getDateFromSchedule(Schedule s){

        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        Date testDate = null;
        String timeString;
        long timeOffset;

        try {
            testDate = fmt.parse(s.getDay_to_take());
            timeString = s.getTime_to_take();
            timeOffset = Long.parseLong(timeString.split(":")[0])*3600000 + Long.parseLong(timeString.split(":")[1])*60000;
            testDate = new Date(testDate.getTime() + timeOffset);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return testDate;
    }

}
