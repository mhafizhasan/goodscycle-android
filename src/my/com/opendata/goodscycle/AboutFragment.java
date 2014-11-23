package my.com.opendata.goodscycle;

import android.annotation.SuppressLint;
import android.app.Fragment;
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

public class AboutFragment extends Fragment {
	
	Button btnNavigate;
	//WebView webView;
	WebSettings webSettings;
	
	InternetDetector internet;
	boolean internetOn = false;
	
	public AboutFragment() {}
	
	
	@SuppressLint("SetJavaScriptEnabled")
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
				
		View rootView = inflater.inflate(R.layout.fragment_about, container, false);
		
		final WebView webView = (WebView) rootView.findViewById(R.id.aboutWebView);
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
				
		webView.loadUrl(SERVER_URL_ABOUT);
		webView.setWebViewClient(new myWebViewClient());

		} else {
			
		webView.loadUrl(LOCAL_URL_OFFLINE);
		
		}
		return rootView;
		
	}
	
	private class myWebViewClient extends WebViewClient {

		 @Override
	     public void onPageStarted(WebView view, String url, Bitmap favicon) {

	      super.onPageStarted(view, url, favicon);
	      getView().findViewById(R.id.progressBar1).setVisibility(View.VISIBLE);
	      getView().findViewById(R.id.textView1).setVisibility(View.VISIBLE);

	     }
		
		
		
		@Override
		public void onPageFinished(WebView view, String url) {

			getView().findViewById(R.id.progressBar1).setVisibility(View.GONE);
			getView().findViewById(R.id.textView1).setVisibility(View.GONE);
		}

		
	} 
	
	public class WebAppInterface {
		
		AboutFragment df;
		
		WebAppInterface(AboutFragment dashboardFragment) {
			
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
	}
	
}
