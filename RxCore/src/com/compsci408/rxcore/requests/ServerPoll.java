package com.compsci408.rxcore.requests;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;

/**
 * Class for handling long polling
 * to the remote server to ensure
 * continued connection and up-to-date
 * data.  Extends {@link Service} to 
 * avoid battery drainage.
 * @author Evan
 */
public class ServerPoll extends Service {

	private IBinder mBinder = new SocketServerBinder();
    private Timer mTimer;
    private boolean mRunning = false;

    @Override
    public void onCreate() {
            super.onCreate();
            mTimer = new Timer();
            mTimer.schedule(new TimerTask() {

                    @Override
                    public void run() {

                            if (mRunning) {
                                    //TODO: Make network call
                            }
                    }
            }, 10000, 10000);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
            mRunning = true;
            return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent arg0) {
            mRunning = true;
            return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
            mRunning = false;
            return super.onUnbind(intent);
    }

    public class SocketServerBinder extends Binder {

            public ServerPoll getService() {
                    return ServerPoll.this;
            }

    }
    
    class MakePoll extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Implement polling
			return null;
		}
    	
    }
	
	

}
