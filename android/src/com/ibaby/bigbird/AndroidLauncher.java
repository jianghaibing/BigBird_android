package com.ibaby.bigbird;

import android.content.Intent;
import android.net.Uri;
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
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.GameHelper;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.facebook.controller.UMFacebookHandler;
import com.umeng.socialize.instagram.controller.UMInstagramHandler;
import com.umeng.socialize.instagram.media.InstagramShareContent;
import com.umeng.socialize.line.controller.UMLineHandler;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.whatsapp.controller.UMWhatsAppHandler;

import android.os.Handler;


public class AndroidLauncher extends AndroidApplication implements AdHandler,PlayServices,ShareInterface{
	private static final String TAG = "AndroidLauncher";
	private static final int SHOW_ADS = 1 ;
	private static final int HIDE_ADS = 0;
	private static final String BAN_ID = "ca-app-pub-6026937026381965/7002164939";
	protected AdView adView;

	private GameHelper gameHelper;
	private final static int requestCode = 1;

	final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");


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

		// 设置分享内容
		mController.setShareContent("I am playing [Bird VS Wind],come and play with me. IOS download: https://itunes.apple.com/us/app/bird-vs-wind/id1077626569?l=zh&ls=1&mt=8 Android download:https://play.google.com/store/apps/details?id=com.ibaby.bigbird ");
		// 设置分享图片, 参数2为图片的url地址
		//mController.setShareMedia(new UMImage(getApplicationContext(), "https://is1-ssl.mzstatic.com/image/thumb/Purple69/v4/50/c1/9c/50c19cce-d29e-2b84-19e1-6161213c10be/pr_source.png/150x150bb.jpg"));

		UMFacebookHandler mFacebookHandler = new UMFacebookHandler(this);
		mFacebookHandler.addToSocialSDK();

		mController.getConfig().supportAppPlatform(getApplicationContext(), SHARE_MEDIA.TWITTER,
				"com.umeng.share", true);

//		// 构建Instagram的Handler
//		UMInstagramHandler instagramHandler = new UMInstagramHandler(getApplicationContext());
//		// 将instagram添加到sdk中
//		instagramHandler.addToSocialSDK();
//		// 本地图片
//		UMImage localImage = new UMImage(getApplicationContext(), R.drawable.ic_launcher);
//		// 设置分享到Instagram的内容， 注意由于instagram客户端的限制，目前该平台只支持纯图片分享，文字、音乐、url图片等都无法分享。
//		InstagramShareContent instagramShareContent = new InstagramShareContent(localImage);
//		// 设置Instagram的分享内容
//		mController.setShareMedia(instagramShareContent);
		// 添加LINE平台
		UMLineHandler lineHandler = new UMLineHandler(getApplicationContext());
		lineHandler.addToSocialSDK();

		// 添加WhatsApp平台
		UMWhatsAppHandler whatsAppHandler = new UMWhatsAppHandler(getApplicationContext());
		whatsAppHandler.addToSocialSDK();

		mController.getConfig().setPlatforms(SHARE_MEDIA.FACEBOOK, SHARE_MEDIA.TWITTER,SHARE_MEDIA.LINE,SHARE_MEDIA.WHATSAPP);


		gameHelper = new GameHelper(this,GameHelper.CLIENT_GAMES);
		gameHelper.enableDebugLog(false);

		GameHelper.GameHelperListener gameHelperListener = new GameHelper.GameHelperListener() {
			@Override
			public void onSignInFailed() {
				Gdx.app.log("Login","登录失败");
			}

			@Override
			public void onSignInSucceeded() {
				Gdx.app.log("Login","登录成功");
			}
		};
		gameHelper.setup(gameHelperListener);

		RelativeLayout layout = new RelativeLayout(this);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		View gameView = initializeForView(new BigBirdGame(this,this,this), config);
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
		builder.addTestDevice("F837025308B47710471BAA749C5153AF");
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT
		);
		layout.addView(adView,params);

		adView.loadAd(builder.build());
		setContentView(layout);
	}

	@Override
	protected void onStart() {
		super.onStart();
		gameHelper.onStart(this);
	}

	@Override
	protected void onStop() {
		super.onStop();
		gameHelper.onStop();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		gameHelper.onActivityResult(requestCode, requestCode, data);

		// 根据requestCode获取对应的SsoHandler
		UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(
				requestCode);
		if (ssoHandler != null) {
			ssoHandler.authorizeCallBack(requestCode, resultCode, data);
		}
	}

	@Override
	public void showAds(boolean show) {
		handler.sendEmptyMessage(show ? SHOW_ADS : HIDE_ADS);
	}

	@Override
	public void signIn() {
		try
		{
			runOnUiThread(new Runnable()
			{
				@Override
				public void run()
				{
					gameHelper.beginUserInitiatedSignIn();
				}
			});
		}
		catch (Exception e)
		{
			Gdx.app.log("MainActivity", "Log in failed: " + e.getMessage() + ".");
		}
	}

	@Override
	public void signOut() {
		try
		{
			runOnUiThread(new Runnable()
			{
				@Override
				public void run()
				{
					gameHelper.signOut();
				}
			});
		}
		catch (Exception e)
		{
			Gdx.app.log("MainActivity", "Log out failed: " + e.getMessage() + ".");
		}
	}

	@Override
	public void rateGame() {
		String str = "这里写playstore的地址";
		startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(str)));
	}

	@Override
	public void unlockAchievement(String achievementID) {
		if (isSignedIn() == true) {
			Games.Achievements.unlock(gameHelper.getApiClient(), achievementID);
		}
	}

	@Override
	public void submitScore(int highScore) {
		if (isSignedIn() == true)
		{
			Games.Leaderboards.submitScore(gameHelper.getApiClient(),getString(R.string.leaderboard_best_flying), highScore);
		}
	}

	@Override
	public void showAchievement() {
		if (isSignedIn() == true)
		{
			startActivityForResult(Games.Achievements.getAchievementsIntent(gameHelper.getApiClient()),requestCode);
		}
		else
		{
			signIn();
		}
	}

	@Override
	public void showScore() {
		if (isSignedIn() == true)
		{
			startActivityForResult(Games.Leaderboards.getLeaderboardIntent(gameHelper.getApiClient(),getString(R.string.leaderboard_best_flying)), requestCode);
		}
		else
		{
			signIn();
		}
	}

	@Override
	public boolean isSignedIn() {
		return gameHelper.isSignedIn();
	}

	@Override
	public void share() {

		try
		{
			runOnUiThread(new Runnable()
			{
				@Override
				public void run() {

					mController.openShare(AndroidLauncher.this, false);

				}
			});
		}
		catch (Exception e)
		{
			Gdx.app.log("ShareActivity", "ShareFailed: " + e.getMessage() + ".");
		}


	}
}
