package my.com.opendata.goodscycle;

import static my.com.opendata.goodscycle.Env.TAG;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.Vibrator;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.widget.Toast;

public class AlarmManagerBroadcastReceiver extends BroadcastReceiver {
	
	final public static String ONE_TIME = "onetime";

	 @Override
	 public void onReceive(Context context, Intent intent) {
		 
	   PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
	         PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, TAG);
	         //Acquire the lock
	         wl.acquire();

	         /*//You can do the processing here.
	         Bundle extras = intent.getExtras();
	         StringBuilder msgStr = new StringBuilder();
	         
	         if(extras != null && extras.getBoolean(ONE_TIME, Boolean.FALSE)){
	          //Make sure this intent has been sent by the one-time timer button.
	          msgStr.append("One time Timer : ");
	         }
	         Format formatter = new SimpleDateFormat("hh:mm:ss a");
	         msgStr.append(formatter.format(new Date()));

	         Toast.makeText(context, msgStr, Toast.LENGTH_LONG).show();*/
	         
	        String pname = intent.getStringExtra("PNAME");
	 		String plat = intent.getStringExtra("PLAT");
	 		String plng = intent.getStringExtra("PLON");
	 		
	 		 Toast.makeText(context, "My Service Started "+pname, Toast.LENGTH_LONG).show();
	 		
	 		 Log.i(TAG, "Reminder Service started "+pname);
	 		 
	 		 // Navigation Pending Intent
	 		 
	 		 /*String url = "http://maps.google.com/maps?f=d&daddr="+plat+","+plng;
	 		 Log.i(TAG, "lat:"+plat+" lon:"+plng);
	 			Intent i = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(url));
	 			i.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
	 			//startActivity(intent);
*/	 			
	 		/*Intent i = new Intent(context, MainActivity.class); 		 
 			PendingIntent pIntent = PendingIntent.getActivity(context, 0, i, 0);*/
	 			
	 			// Main Intent
	 			/*Intent iMain = new Intent(context, MainActivity.class);
	 			TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);*/
	 			
	 			// Passing params to NavigationActivity
	 			Intent iNavigation = new Intent(context , NavigationActivity.class); 
	 			iNavigation.putExtra("PNAME", pname);
	 			iNavigation.putExtra("PLAT", plat);
	 			iNavigation.putExtra("PLON", plng);
	 			
	 			PendingIntent pIntent = PendingIntent.getActivity(context, 0, iNavigation, 0);
	 			
	 			Notification n = new Notification.Builder(context)
	 					.setContentTitle("Reminder Task")
	 					.setContentText(pname)
	 					.setSmallIcon(R.drawable.ic_icon)
	 					.setContentIntent(pIntent)
	 					.setAutoCancel(true)
	 					.addAction(android.R.drawable.ic_menu_directions, "Navigate", pIntent).build();
	 			
	 			NotificationManager notificationManager = 
	 					(NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
	 			
	 			notificationManager.notify(0, n);
	         
	 			// vibrate 
	 			Vibrator v;
	 			v=(Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
	 			v.vibrate(3000);
	         
	         //Log.i(TAG, "On RECEIVE");
	         
	         //Release the lock
	         wl.release();
	 }

	    public void CancelAlarm(Context context)
	    {
	        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
	        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
	        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
	        alarmManager.cancel(sender);
	    }

	    public void setOnetimeTimer(Context context, Intent intent) {
	    	
	    	AlarmManager am =(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
	    	
	    	String pname = intent.getStringExtra("PNAME");
			String plat = intent.getStringExtra("PLAT");
			String plng = intent.getStringExtra("PLON");
			String pdate = intent.getStringExtra("PDATE");
			
			Log.i(TAG, "pdate:"+pdate);
			
			String[] splitDate = pdate.split("-");
			String sDay = splitDate[0];
			String sMonth = splitDate[1];
			String sYear = splitDate[2];
			
			int iDay = Integer.parseInt(sDay);
			int iMonth = Integer.parseInt(sMonth);
				iMonth = iMonth - 1;
			int iYear = Integer.parseInt(sYear);
			
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(System.currentTimeMillis());
	        calendar.clear();
			calendar.set(iYear, iMonth, iDay, 7, 30);

	        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
	        am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pi);	
	  
	    }

}
