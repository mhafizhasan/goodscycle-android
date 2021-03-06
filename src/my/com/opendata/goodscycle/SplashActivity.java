package my.com.opendata.goodscycle;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

public class SplashActivity extends Activity {
	
	TextView txtVersion;
    String version;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        
        //Create an object of type SplashHandler
        SplashHandler mHandler = new SplashHandler();
        // set the layout for this activity
        setContentView(R.layout.activity_splash);
        
      txtVersion = (TextView) findViewById(R.id.appversion);
      
      
	try {
			PackageInfo pInfo;
			pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
			version = pInfo.versionName;
			
			txtVersion.setText("version "+version);
			
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			txtVersion.setText("version");
		}
        
        // Create a Message object
        Message msg = new Message();
        //Assign a unique code to the message.
        //Later, this code will be used to identify the message in Handler class.
        msg.what = 0;
        // Send the message with a delay of 3 seconds(3000 = 3 sec).
        mHandler.sendMessageDelayed(msg, 3000);
    }     
    
    // Handler class implementation to handle the message
    private class SplashHandler extends Handler {
        
        //This method is used to handle received messages
        @Override
		public void handleMessage(Message msg)
          {
            // switch to identify the message by its code
            switch (msg.what)
            {
            default:
            case 0:
              super.handleMessage(msg);
              
              //Create an intent to start the new activity.
              // Our intention is to start MainActivity
              Intent intent = new Intent();
              intent.setClass(SplashActivity.this,MainActivity.class);
              startActivity(intent);
              // finish the current activity
              SplashActivity.this.finish();
            }
          }
    }    
   
}