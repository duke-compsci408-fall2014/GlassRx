package com.compsci408.rxcore.requests;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
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

	PollReceiver receiver = new PollReceiver();
    public void onCreate()
    {
        super.onCreate();       
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) 
{
         receiver.SetAlarm(ServerPoll.this);
     return START_STICKY;
}



    public void onStart(Context context,Intent intent, int startId)
    {
        receiver.SetAlarm(context);
    }

    @Override
    public IBinder onBind(Intent intent) 
    {
        return null;
    }
	
	

}
