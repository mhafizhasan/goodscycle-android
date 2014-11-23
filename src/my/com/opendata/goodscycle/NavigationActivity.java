package my.com.opendata.goodscycle;

import static my.com.opendata.goodscycle.Env.TAG;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

public class NavigationActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		String pname = intent.getStringExtra("PNAME");
 		String plat = intent.getStringExtra("PLAT");
 		String plng = intent.getStringExtra("PLON");
		
		String url = "http://maps.google.com/maps?f=d&daddr="+plat+","+plng;
		Log.i(TAG, "lat:"+plat+" lon:"+plng);
		Intent i = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(url));
		i.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
		startActivity(i);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		
	}

	
}
