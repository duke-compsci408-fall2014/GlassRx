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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RemoteViews;

import com.compsci408.glassrx.glassrx2014.R;
import com.google.android.glass.timeline.LiveCard;
import com.google.android.glass.timeline.LiveCard.PublishMode;

import java.util.Timer;
import java.util.TimerTask;

/**
 * A {@link Service} that publishes a {@link LiveCard} in the timeline.
 */
public class NextMedLiveCard extends Service {

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

    private Timer heartbeat;

    private RemoteViews remoteViews;

    private final Runnable mUpdateTextRunnable = new Runnable() {

        @Override
        public void run() {
//            if(mRunning) {
                postDelayed(mUpdateTextRunnable, DELAY_MILLIS);
                updateText("It's time to take your medicine:\nName: Aricept\nTime: 7pm\nDosage: 100mg" + count);
//                mRunning = false;
                count++;
//            }

        }
    };

    public void nextAlarm(String cardText){
        remoteViews = new RemoteViews(getPackageName(), R.layout.next_med_live_card);
        remoteViews.setCharSequence(R.id.text_view, "setText", cardText);
        mLiveCard.setViews(remoteViews);

        Intent menuIntent = new Intent(this, LiveCardMenuActivity.class);
        mLiveCard.setAction(PendingIntent.getActivity(this, 0, menuIntent, 0));
        
        
        setAlarm("Stuffffff", 6000);
        
    }
    
    public boolean flag = true;
    public boolean flag2 = true;

    public void updateText(String cardText){
        boolean wasPublished = mLiveCard.isPublished();
        if(wasPublished && flag) {
            mLiveCard.unpublish();
        }
        remoteViews = new RemoteViews(getPackageName(), R.layout.next_med_live_card);
        remoteViews.setCharSequence(R.id.text_view, "setText", cardText);
        mLiveCard.setViews(remoteViews);

        Intent menuIntent = new Intent(this, LiveCardMenuActivity.class);
        mLiveCard.setAction(PendingIntent.getActivity(this, 0, menuIntent, 0));

        if(flag2) {
            playSound();
            flag2 = false;
        }

        if(wasPublished && flag) {
            mLiveCard.publish(PublishMode.REVEAL);
        }
        if(flag) {
            setAlarm("Next Medication: Flintstones\nTime: 7am Tomorrow\nDosage: 2 pills\n", 5000);
            flag = false;
        }


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

        if (mLiveCard == null) {

            mLiveCard = new LiveCard(this, LIVE_CARD_TAG);

            remoteViews = new RemoteViews(getPackageName(), R.layout.next_med_live_card);


            remoteViews.setCharSequence(R.id.text_view, "setText", "Next Medication: Aricept\nTime: 7pm\n Dose: 100mg" +
                    "\n\nTap for more options");

            mLiveCard.setViews(remoteViews);

            // Display the options menu when the live card is tapped.
            Intent menuIntent = new Intent(this, LiveCardMenuActivity.class);
            mLiveCard.setAction(PendingIntent.getActivity(this, 0, menuIntent, 0));
            mLiveCard.publish(PublishMode.REVEAL);
        } else {
            mLiveCard.navigate();
        }

        mSoundPool = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0);
        mTimerFinishedSoundId = mSoundPool.load(this, R.raw.timer_finished, SOUND_PRIORITY);

        if(heartbeat == null) {
            heartbeat = new Timer();
        }

        setAlarm("It's time to take your Aricept!\nTime: 7pm\nDosage:100mg\n", 15000);


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

    private void setAlarm(final String s, long time)
    {
        final Handler handler = new Handler();
        TimerTask liveCardUpdateTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            updateText(s);
                        } catch (Exception e) {

                        }
                    }
                });
            }
        };
        heartbeat.schedule(liveCardUpdateTask, time);

    }

}
