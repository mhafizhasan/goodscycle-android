package my.com.opendata.goodscycle;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import static my.com.opendata.goodscycle.Env.*;

public class ReminderService extends Service {

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		/*NotificationManager mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
	    Notification notification = new Notification(R.drawable.ic_launcher, "Reminder Start", System.currentTimeMillis());
	    Intent myIntent = new Intent(this , MainActivity.class);     
	    PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, 0);
	    notification.setLatestEventInfo(this, "Notify label", "Notify text", contentIntent);
	    mNM.notify(NOTIFICATION, notification);*/
		
		
		
		Toast.makeText(this, "My Service Created", Toast.LENGTH_LONG).show();
		Log.i(TAG, "Reminder Service created");
	}
	
	@Override
	 public void onStart(Intent intent, int startid) {
		
		
	 }

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		String pname = intent.getStringExtra("PNAME");
		String plat = intent.getStringExtra("PLAT");
		String plng = intent.getStringExtra("PLON");
		
		 Toast.makeText(this, "My Service Started "+pname, Toast.LENGTH_LONG).show();
		 Log.i(TAG, "Reminder Service started "+pname);
		 
		 String url = "http://maps.google.com/maps?f=d&daddr="+plat+","+plng;
			Intent i = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(url));
			i.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
			//startActivity(intent);
			
			PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext(), 0, i, 0);
			
			Notification n = new Notification.Builder(getApplicationContext())
					.setContentTitle("Reminder Task")
					.setContentText(pname)
					.setSmallIcon(R.drawable.ic_icon)
					.setContentIntent(pIntent)
					.setAutoCancel(true)
					.addAction(android.R.drawable.ic_menu_directions, "Navigate", pIntent).build();
			
			NotificationManager notificationManager = 
					(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
			
			notificationManager.notify(0, n);
		
		return Service.START_STICKY;
	}
	
	

}
