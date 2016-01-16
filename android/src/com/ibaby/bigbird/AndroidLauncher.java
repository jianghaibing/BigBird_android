package com.ibaby.bigbird;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.RelativeLayout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import android.os.Handler;


public class AndroidLauncher extends AndroidApplication implements AdHandler {
	private static final String TAG = "AndroidLauncher";
	private static final int SHOW_ADS = 1 ;
	private static final int HIDE_ADS = 0;
	private static final String BAN_ID = "ca-app-pub-6026937026381965/7002164939";
	protected AdView adView;

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what){
				case SHOW_ADS:
					adView.setVisibility(View.VISIBLE);
					break;
				case HIDE_ADS:
					adView.setVisibility(View.GONE);
					break;

			}
		}
	};

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		RelativeLayout layout = new RelativeLayout(this);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		View gameView = initializeForView(new BigBirdGame(this), config);
		layout.addView(gameView);
		adView = new AdView(this);
		adView.setAdListener(new AdListener() {
			@Override
			public void onAdLoaded() {
				super.onAdLoaded();
				Gdx.app.log("AD:", "AD is loaded");
			}
		});
		adView.setAdSize(AdSize.SMART_BANNER);
		adView.setAdUnitId(BAN_ID);
		AdRequest.Builder builder = new AdRequest.Builder();
		builder.addTestDevice("");
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT
		);
		layout.addView(adView,params);

		adView.loadAd(builder.build());
		setContentView(layout);
	}

	@Override
	public void showAds(boolean show) {
		handler.sendEmptyMessage(show ? SHOW_ADS : HIDE_ADS);
	}
}
