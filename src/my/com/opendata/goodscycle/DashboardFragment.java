package my.com.opendata.goodscycle;

import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Fragment;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import static my.com.opendata.goodscycle.Env.*;

public class DashboardFragment extends Fragment {
	
	Button btnNavigate;
	WebView webView;
	WebSettings webSettings;
	
	InternetDetector internet;
	boolean internetOn = false;
	
	private AlarmManagerBroadcastReceiver alarm;
	
	public DashboardFragment() {}
	
	@SuppressLint("SetJavaScriptEnabled")
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
				
		alarm = new AlarmManagerBroadcastReceiver();

		View rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);
		
		webView = (WebView) rootView.findViewById(R.id.dashboardWebView);
		webView.addJavascriptInterface(new WebAppInterface(this), "Android");
		
		internet = new InternetDetector(getActivity().getApplicationContext());
		internetOn = internet.isConnectingToInternet();
		
		if(internetOn) {
		
			webSettings = webView.getSettings();
			webSettings.setJavaScriptEnabled(true);
		
//		webView.setInitialScale(1);
//		webView.getSettings().setJavaScriptEnabled(true);
//		webView.getSettings().setLoadWithOverviewMode(true);
//		webView.getSettings().setUseWideViewPort(true);
//		webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
//		webView.setScrollbarFadingEnabled(true);
//		webView.getSettings().setBuiltInZoomControls(false);
				
			webView.loadUrl(SERVER_URL);
			webView.setWebViewClient(new myWebViewClient());
		
		} else {
		
			webView.loadUrl(LOCAL_URL_OFFLINE);
		
		}
		
		return rootView;
		
	}
	
	/*public void onetimeTimer(View view){
	     Context context = getActivity().getApplicationContext();
	     if(alarm != null){
	      alarm.setOnetimeTimer(context);
	     }else{
	      Toast.makeText(context, "Alarm is null", Toast.LENGTH_SHORT).show();
	     }
	    }*/
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		//Log.i(TAG, "On Resume");
	}



	private class myWebViewClient extends WebViewClient {

		 @Override
	     public void onPageStarted(WebView view, String url, Bitmap favicon) {

	      super.onPageStarted(view, url, favicon);
	      getView().findViewById(R.id.progressBarDB).setVisibility(View.VISIBLE);
	      getView().findViewById(R.id.textViewDB).setVisibility(View.VISIBLE);

	     }
		
		
		
		@Override
		public void onPageFinished(WebView view, String url) {

			getView().findViewById(R.id.progressBarDB).setVisibility(View.GONE);
			getView().findViewById(R.id.textViewDB).setVisibility(View.GONE);
		}

		
	} 
	
	public class WebAppInterface {
		
		DashboardFragment df;
		
		WebAppInterface(DashboardFragment dashboardFragment) {
			
			df = dashboardFragment;
		}
		
		@JavascriptInterface
		public void startNavigation(String lat, String lng) {
	        
//			Toast.makeText(getActivity().getApplicationContext(),
//					"", Toast.LENGTH_SHORT).show();
	        
	        String url = "http://maps.google.com/maps?f=d&daddr="+lat+","+lng;
    		Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(url));
    		intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
    		startActivity(intent);
	    }
		
		@JavascriptInterface
		public void setReminder(String pname, String pdate, String plat, String plon) {
			
			Toast.makeText(getActivity().getApplicationContext(), 
					"Reminder for " + pname + " created", Toast.LENGTH_SHORT).show();
			
			//Context context = getActivity().getApplicationContext();
		     if(alarm != null) {
		    	 
		    	Intent i = new Intent(getActivity() , AlarmManagerBroadcastReceiver.class); 
				i.putExtra("PNAME", pname);
				i.putExtra("PDATE", pdate);
				i.putExtra("PLAT", plat);
				i.putExtra("PLON", plon);
					
		    	 alarm.setOnetimeTimer(getActivity().getApplicationContext(), i);
		    	 
		     } else {
		    	 Toast.makeText(getActivity().getApplicationContext(), 
		    			 "Alarm is null", Toast.LENGTH_SHORT).show();
		     }
			
/*			Calendar calendar = Calendar.getInstance();
			calendar.set(2014, 3, 26);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MINUTE, 38);
			calendar.set(Calendar.HOUR_OF_DAY, 1);
			//calendar.set(Calendar.HOUR, 11);
			//calendar.set(Calendar.AM_PM, Calendar.AM);
			//calendar.add(Calendar.DAY_OF_MONTH, 26);
		

			Intent i = new Intent(getActivity() , ReminderService.class); 
			i.putExtra("PNAME", pname);
			i.putExtra("PDATE", pdate);
			i.putExtra("PLAT", plat);
			i.putExtra("PLON", plon);
			
			PendingIntent pIntent = PendingIntent.getService(getActivity(), 0, i, 0);
			
			AlarmManager alarmManager = (AlarmManager)getActivity().getApplicationContext().getSystemService(Service.ALARM_SERVICE);
			alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pIntent);
			getActivity().getApplicationContext().startService(i);*/
		}
	}
	
}
