package com.compsci408.glassrx.provider;
import java.util.Random;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.widget.RemoteViews;

import com.compsci408.glassrx.R;
import com.google.android.glass.timeline.LiveCard;
import com.google.android.glass.timeline.LiveCard.PublishMode;

public class LowFreqLiveCardService extends Service {

    private static final String LIVE_CARD_TAG = "LiveCardDemo";

    private LiveCard mLiveCard;
    private RemoteViews mLiveCardView;

    private int homeScore, awayScore;
    private Random mPointsGenerator;

    private final Handler mHandler = new Handler();
    private final UpdateLiveCardRunnable mUpdateLiveCardRunnable =
        new UpdateLiveCardRunnable();
    private static final long DELAY_MILLIS = 60000;

    @Override
    public void onCreate() {
        super.onCreate();
        mPointsGenerator = new Random();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (mLiveCard == null) {

            // Get an instance of a live card
            mLiveCard = new LiveCard(this, LIVE_CARD_TAG);

            // Inflate a layout into a remote view
            mLiveCardView = new RemoteViews(getPackageName(),
                R.layout.activity_main);

          // Set up initial RemoteViews values
            homeScore = 0;
            awayScore = 0;
            mLiveCardView.setTextViewText(R.id.updated_pill_info,
                    "No pills to take right now!");
            mLiveCardView.setTextViewText(R.id.time_to_take_pill,
                    "Your next pill is in: 2 minutes");

            // Set up the live card's action with a pending intent
            // to show a menu when tapped
            Intent menuIntent = new Intent(this, ProviderMainActivity.class);
            menuIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TASK);
            mLiveCard.setAction(PendingIntent.getActivity(
                this, 0, menuIntent, 0));

            // Publish the live card
            mLiveCard.publish(PublishMode.REVEAL);

            // Queue the update text runnable
            mHandler.post(mUpdateLiveCardRunnable);
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if (mLiveCard != null && mLiveCard.isPublished()) {
          //Stop the handler from queuing more Runnable jobs
            //mUpdateLiveCardRunnable.setStop(true);

            mLiveCard.unpublish();
            mLiveCard = null;
        }
        super.onDestroy();
    }

    /**
     * Runnable that updates live card contents
     */
    private class UpdateLiveCardRunnable implements Runnable{

        private boolean mIsStopped = false;

        /*
         * Updates the card with a fake score every 30 seconds as a demonstration.
         * You also probably want to display something useful in your live card.
         *
         * If you are executing a long running task to get data to update a
         * live card(e.g, making a web call), do this in another thread or
         * AsyncTask.
         */
        @Override
		public void run(){


                // Update the remote view with current pill info and make a notification somehow
                mLiveCardView.setTextViewText(R.id.updated_pill_info,
                    "Please take your meds now!");

                // Always call setViews() to update the live card's RemoteViews.
                mLiveCard.setViews(mLiveCardView);

                // Queue another score update in 30 seconds.
                mHandler.postDelayed(mUpdateLiveCardRunnable, DELAY_MILLIS);
            }
        }
//
//        public boolean isStopped() {
//            return mIsStopped;
//        }
//
//        public void setStop(boolean isStopped) {
//            this.mIsStopped = isStopped;
//        }
//    }

    @Override
    public IBinder onBind(Intent intent) {
      /*
public class LowFreqLiveCardService {

}
       *  If you need to set up interprocess communication
       * (activity to a service, for instance), return a binder object
       * so that the client can receive and modify data in this service.
       *
       * A typical use is to give a menu activity access to a binder object
       * if it is trying to change a setting that is managed by the live card
       * service. The menu activity in this sample does not require any
       * of these capabilities, so this just returns null.
       */
        return null;
    }
}

